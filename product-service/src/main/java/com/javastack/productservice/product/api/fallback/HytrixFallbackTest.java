package com.javastack.productservice.product.api.fallback;

import com.javastack.productservice.product.entity.Product;
import com.javastack.productservice.product.service.ProductService;
import com.netflix.hystrix.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Future;


public class HytrixFallbackTest extends HystrixCommand<Product> {

//    @Autowired
    private ProductService productService;

    //要实现缓存，必须初始化这个name，这个也是缓存key
    private Long productid;

    public HytrixFallbackTest(ProductService productService, Long productid) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("productGroupkey"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("detailCommand"))
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("detailThreadpoolkey"))
                .andCommandPropertiesDefaults(
                        HystrixCommandProperties.Setter()
                                .withExecutionTimeoutInMilliseconds(5000)
                                .withRequestCacheEnabled(true)
                                .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE)
                                .withRequestLogEnabled(true)
                                .withRequestCacheEnabled(true) //实现缓存


                )
                .andThreadPoolPropertiesDefaults(
                        HystrixThreadPoolProperties.Setter()
                                .withQueueSizeRejectionThreshold(20))
        );
        this.productService = productService;
        this.productid = productid;
    }

    @Override
    protected Product getFallback() {
        //return super.getFallback();
//        return Collections.EMPTY_LIST;
        return  new Product("default_product_name", "/default/cover/00" + 1 + ".png", 0);
    }

    //实现缓存
    @Override
    protected String getCacheKey() {
        return productid + "";
    }

    @Override
    protected Product run() throws Exception {
        return productService.getProduct(productid + "");
//        return null;
    }
}
