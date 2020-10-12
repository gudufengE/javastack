
package com.javastack.productservice.product.api;

import com.javastack.productservice.product.api.fallback.HytrixFallbackTest;
import com.javastack.productservice.product.entity.Product;
import com.javastack.productservice.product.entity.ProductComment;
import com.javastack.productservice.product.redis.UserRedisRepository;
import com.javastack.productservice.product.repository.ProductCommentRepository;
import com.javastack.productservice.product.repository.ProductRepository;
import com.javastack.productservice.product.service.ProductService;
import com.javastack.productservice.product.service.UserService;
import com.javastack.publiccomponent.utils.DateUtils;
import com.javastack.publiccomponent.utils.IdUtils;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.annotation.ObservableExecutionMode;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheKey;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 商品管理的Endpoint
 */
@RestController
@RequestMapping("/products")
@Api(tags = "产品信息", description = "产品信息", value = "产品信息")
public class ProductEndpoint {
//    @Value("${foo}")
//    private String foo;


    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    protected Logger logger = LoggerFactory.getLogger(ProductEndpoint.class);

    @Autowired
    @Qualifier(value = "restTemplate")
    private RestTemplate restTemplate;

    /**
     * 获取商品列表1
     *
     * @return
     */
    @ApiOperation(value="产品列表", position = 1, notes="产品列表")
    @ApiImplicitParams({
    })
    @RequestMapping(method = RequestMethod.GET)
    public List<Product> list() {
        return productService.list();
    }




    @ApiOperation(value="产品详情，使用继承类来实现", position = 2, notes="产品详情，使用继承类来实现")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public Product detail2(@PathVariable Long id) {
        HytrixFallbackTest hytrixFallbackTest = new HytrixFallbackTest(productService, id);
        return hytrixFallbackTest.execute();
    }



    /**
     * 获取指定商品的详情
     *
     * @param id 商品的Id
     * @return
     */
    @HystrixCommand(fallbackMethod = "detailBack" ,
            ignoreExceptions = HystrixBadRequestException.class,
            observableExecutionMode = ObservableExecutionMode.LAZY,
            commandKey = "ProductEndpoint_detail"/*,commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value = "2000")
    }*/)  //虽然是微服务上提出的，但普通的方法上也可以做熔断机制
    @ApiOperation(value="产品详情，使用注解实现熔断", position = 3, notes="产品详情")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Product detail(@PathVariable Long id) {
        //超时--打开熔断
//        try {
//            TimeUnit.SECONDS.sleep(4);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        //返回运行时异常--打开熔断
        if (id.longValue() == 2){
//            return null;  //静默模式：返回各种空，如：null、空list或空map等
//            return defaultvalue;  //返回默认值
//              return request.getRemoteAddress().getPort(); //返回包含前端来的值
//            return redis.getkey();  //返回远程缓存值

            throw new RuntimeException("快速失败"); //快速失败模式：直接抛出异常
        }
        return productService.getProduct(id + "");
//        return this.productRepository.findOne(id);//todo
    }

    public Product detailBack(Long id) {
        Product u = new Product("default_product_name", "/default/cover/00" + id + ".png", 0);
        return u;
//        return this.productRepository.findOne(id);//todo
    }






    /**
     * 获取指定商品的评论列表
     *
     * @param id 商品的Id
     * @return
     */
    @ApiOperation(value="产品评论列表", position = 4, notes="产品评论列表")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/{id}/comments", method = RequestMethod.GET)
    public List<ProductCommentDto> comments(@PathVariable Long id) {
        List<ProductComment> commentList = productService.findByProductIdOrderByCreated(id);
        if (null == commentList || commentList.isEmpty())
            return Collections.emptyList();

        return commentList.stream().map((comment) -> {
            ProductCommentDto dto = new ProductCommentDto(comment);

            dto.setProduct(productService.getProduct(comment.getProductId() + ""));
//            dto.setAuthor(this.loadUser(comment.getAuthorId()));
            dto.setAuthor(this.loadUserEx(comment.getAuthorId())); //消息驱动的微服务架构，使用redis缓存
            return dto;
        }).collect(Collectors.toList());
    }

    /**
     * 通过RestTemplate加载评论作者的用户信息
     *
     * @param userId 用户Id
     * @return
     */
    protected UserDto loadUser(Long userId) {
//        UserDto userDto = this.restTemplate.getForEntity("http://localhost:2100/users/{id}", UserDto.class, userId).getBody();
//        UserDto userDto = this.restTemplate.getForEntity("http://USERSERVICE/users/{id}", UserDto.class, userId).getBody();
        //使用feign
        UserDto userDto = this.userService.load(userId);
        if (userDto != null){
            logger.debug("I come from server : {}", userDto.getUserservicePort());
        }
        return userDto;
    }

    @Autowired
    protected UserRedisRepository userRedisRepository;
    protected UserDto loadUserEx(Long id) {
        UserDto userDto = this.userRedisRepository.findOne(id);
        if (null != userDto) {
            this.logger.debug("已从Redis缓存中获取到用户:{} 的信息", id);
            return userDto;
        }

        this.logger.debug("Redis缓存中不存在用户:{} 的信息，尝试从远程进行加载", id);
        userDto = this.userService.load(id);
        if (null != userDto) {
            this.userRedisRepository.saveUser(userDto);
        }
        return userDto;
    }


    /**
     * FIXME: 这个方法不应该在这里，仅用来演示方便
     * 获取用户信息列表
     * @return
     */
    @ApiOperation(value="获取用户信息列表", position = 2, notes="获取用户信息列表")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/users",method = RequestMethod.GET)
    public List<UserDto> userList() {
        return this.userService.findAll();
    }
}