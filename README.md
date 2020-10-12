**一、springboot多模块开发**
参考：https://juejin.im/post/6844904094558060552#heading-3
 1.1 新建maven项目，然后删除src，然后打开file--Setting，做点最基本的配置：
    maven配置
    Java compiler 配置
    Annotation processors 配置
    
 1.2 编辑pom.xml
  1) 父级pom的坐标
  <groupId>com.pchj</groupId>
  <artifactId>javastack</artifactId>
  <version>1.0-SNAPSHOT</version>
  
 2) 父项目的pom    
 <!--父级项目的packing必须设置为 pom-->
 <packaging>pom</packaging>
 
 3)  dependencies & dependencyManagement 的区别
    dependencies 下的依赖被子项目会继承
    dependencyManagement下的依赖只是声明，子项目不会继承，
    子项目需要的话，还是需要显示引入，子项目引入若没有
    指定具体的version和scope，则取这里的version和scope
    
 4) 子项目的依赖，只要引入坐标,如：
    <dependency>
       <groupId>com.javastack</groupId>
       <artifactId>public-component</artifactId>
       <version>0.0.1-SNAPSHOT</version>
     </dependency>
 打包？todo           
             
**二、通用依赖尽量写到父 pom.xml 中**


三、聊聊spring
    ioc
    aop
    
    实例化  --》  初始化  ：_new__ --》__init__
    
四、微服务架构
   4.1 建立父pom.xml--新建maven工程，完成后删除src
    见：上面的多模块开发
   4.2 euraka server
       1） 新建maven工程
       2） 加入依赖：
            <dependency>
                       <groupId>org.springframework.cloud</groupId>
                       <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
                       <version>2.2.5.RELEASE</version>
                   </dependency>
       3）加入注解
          在spring boot的启动类中加入：@EnableEurekaServer
       4）编写application.properties文件，关键的如下：
          spring.application.name = serviceeureka
          server.port = 8260
          eureka.instance.hostname = localhost
          eureka.server.wait-time-in-ms-when-sync-empty = 5
          eureka.client.register-with-eureka = false
          eureka.client.fetch-registry = false
          logging.level.org.springframework = INFO
   4.3 eureka client
    1）新建mavent工程，继承父pom
       建议新建工程时，加入web功能：
        <!--web-->
               <dependency>
                   <groupId>org.springframework.boot</groupId>
                   <artifactId>spring-boot-starter-thymeleaf</artifactId>
               </dependency>
               <dependency>
                   <groupId>org.springframework.boot</groupId>
                   <artifactId>spring-boot-starter-web</artifactId>
               </dependency>
    2）加入依赖：
       <!--eureka-client-->
               <dependency>
                   <groupId>org.springframework.cloud</groupId>
                   <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
                   <version>2.2.5.RELEASE</version>
               </dependency>
    3）在boot的启动类加入注解：
       @EnableEurekaClient
    4）编写application.properties文件：如：
        server.port=2100
        spring.application.name=userservice
        eureka.instance.prefer-ip-address=true
        eureka.client.register-with-eureka=true
        eureka.client.fetch-registry=true
        eureka.client.service-url.defaultZone=http://localhost:8260/eureka
        logging.level.org.springframework=INFO
    5)查看服务详细信息：
    **http://localhost:8260/eureka/apps**/userservice
    或 打开erueka：   http://localhost:8260 查看服务是否up，一般服务启动到注册成功要30秒后才能在控制台看到，
    因为urueka要去服务提供者必须发3次心跳（默认每次心跳间隔10秒）
     
五、负载均衡
    5.1 Netflix下的子项目ribbon可以和Eureka无缝集成，共同提供客户端负载均衡功能
    5.2 配置服务消费方，如这里的product-service
        eureka.client.fetch-registry=true
    5.3 在服务消费方，加配置：
    <!--ribbon client负载均衡-->
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-starter-netflix-ribbon</artifactId>
                <version>2.2.5.RELEASE</version>
            </dependency>
    5.4 在服务消费方的 rest客户端加注解：@LoadBalanced，如：
    在productservice服务里面：
        @LoadBalanced
        @Bean(value = "restTemplate")
        RestTemplate restTemplate() {
            return new RestTemplate();
        }
    5.5 在服务提供方以多于一个端口启动
    idea中又不能改端口后多实例运行？
    解决：在Edit configuration-->右上角勾选 allow parallel run
    打包找不到public-component？？？？
    解决：？
        java -jar user-service 1.0-SNAPSHOT
        java -jar user-service 1.0-SNAPSHOT --server.port=2110
 
 
 六、feign 代替restTemplate，并天生具有负载均衡的功能
 6.1 引入feign依赖
 <dependency>
              <groupId>org.springframework.cloud</groupId>
              <artifactId>spring-cloud-starter-feign</artifactId>
              <version>1.4.7.RELEASE</version>
         </dependency>
6.2 在spring boot的启动类加个注释：
@EnableFeignClients
注意：所要访问的微服务要在同一个包，如果不在的话就要中basePackage
@EnableFeignClients(basePackage="your.service.base.package.* *")
6.3 建立要访问的service    
/**
 * 用户服务，使用Feign实现
 */
@FeignClient("USERSERVICE")
public interface UserService {
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    List<UserDto> findAll();

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    UserDto load(@PathVariable("id") Long id);
}
6.4 坑：
引入feign依赖 后，启动报错：
Could not find class ... loadbalancer.reactive.OnNoRibbonDefaultCondition
FileNotFoundException:  ... ServerPropertiesAutoConfiguration
原因：springboot和spring cloud版本匹配问题
参考：https://www.cnblogs.com/zhuwenjoyce/p/10261079.html
https://start.spring.io/actuator/info  -- 用postman或fire浏览器 执行看下对应关系
 "spring-cloud": {
            "Finchley.M2": "Spring Boot >=2.0.0.M3 and <2.0.0.M5",
            "Finchley.M3": "Spring Boot >=2.0.0.M5 and <=2.0.0.M5",
            "Finchley.M4": "Spring Boot >=2.0.0.M6 and <=2.0.0.M6",
            "Finchley.M5": "Spring Boot >=2.0.0.M7 and <=2.0.0.M7",
            "Finchley.M6": "Spring Boot >=2.0.0.RC1 and <=2.0.0.RC1",
            "Finchley.M7": "Spring Boot >=2.0.0.RC2 and <=2.0.0.RC2",
            "Finchley.M9": "Spring Boot >=2.0.0.RELEASE and <=2.0.0.RELEASE",
            "Finchley.RC1": "Spring Boot >=2.0.1.RELEASE and <2.0.2.RELEASE",
            "Finchley.RC2": "Spring Boot >=2.0.2.RELEASE and <2.0.3.RELEASE",
            "Finchley.SR4": "Spring Boot >=2.0.3.RELEASE and <2.0.999.BUILD-SNAPSHOT",
            "Finchley.BUILD-SNAPSHOT": "Spring Boot >=2.0.999.BUILD-SNAPSHOT and <2.1.0.M3",
            "Greenwich.M1": "Spring Boot >=2.1.0.M3 and <2.1.0.RELEASE",
            "Greenwich.SR6": "Spring Boot >=2.1.0.RELEASE and <2.1.17.BUILD-SNAPSHOT",
            "Greenwich.BUILD-SNAPSHOT": "Spring Boot >=2.1.17.BUILD-SNAPSHOT and <2.2.0.M4",
            "Hoxton.SR8": "Spring Boot >=2.2.0.M4 and <2.3.4.BUILD-SNAPSHOT",
            "Hoxton.BUILD-SNAPSHOT": "Spring Boot >=2.3.4.BUILD-SNAPSHOT and <2.4.0.M1",
            "2020.0.0-M3": "Spring Boot >=2.4.0.M1 and <=2.4.0.M1",
            "2020.0.0-SNAPSHOT": "Spring Boot >=2.4.0.M2"
        }
同时，如果你是用dependencyManagement在父pom中配置的话，注意其
<type>pom</type>
<scope>import</scope>
不能少啊！如下：
`<dependencyManagement>
        <dependencies>

            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>Hoxton.SR8</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <!-- ... -->
  </dependencies>
  </dependencyManagement>`
   
        
七、eureka集群
    7.1 配置节点1：application-sdpeer1.properties 
server.port = 8260
eureka.instance.hostname = sdpeer1

eureka.instance.prefer-ip-address = true
eureka.instance.ip-address = 192.168.2.102
eureka.client.register-with-eureka = true
eureka.client.fetch-registry = true
eureka.client.service-url.defaultZone = http://sdpeer2:8262/eureka,http://sdpeer3:8263/eureka
    7.2 配置节点2：application-sdpeer2.properties 
server.port = 8262
eureka.instance.hostname = sdpeer2
eureka.instance.prefer-ip-address = true
eureka.instance.ip-address = 192.168.2.102
eureka.client.register-with-eureka = true
eureka.client.fetch-registry = true
eureka.client.service-url.defaultZone = http://sdpeer1:8260/eureka,http://sdpeer3:8263/eureka
    7.3 配置节点3：application-sdpeer3.properties 
server.port = 8263
eureka.instance.hostname = sdpeer3
eureka.instance.prefer-ip-address = true
eureka.instance.ip-address = 192.168.2.102
eureka.client.register-with-eureka = true
eureka.client.fetch-registry = true
eureka.client.service-url.defaultZone = http://sdpeer1:8260/eureka,http://sdpeer2:8262/eureka

7.4 hosts的配置
演示由于是在一台机器上运行多个eureaka服务器，所以在hosts上配置：
127.0.0.1 sdpeer1
127.0.0.1 sdpeer2
127.0.0.1 sdpeer3

7.5 其它微服务的配置，如usersservice：
#单体架构配置
#eureka.client.service-url.defaultZone=http://localhost:8260/eureka
#集群配置 #指定任意一个eureka
eureka.client.service-url.defaultZone=http://sdpeer1:8260/eureka

7.6 eurea -- application.properties 的配置和启动
######################集群配置start######################
spring.profiles.active = sdpeer3
######################集群配置end#########################
修改
spring.profiles.active = sdpeer1
spring.profiles.active = sdpeer2
并分别启动eurea
注意：在idea中，允许其并行运行

7.7 eureka的安全性
https://blog.csdn.net/qq_35881988/article/details/85274318
 1）<!--设置登录密码需要用到Spring Security-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>

2）
#设置eurekaServer的登录密码
spring.security.user.name=admin
spring.security.user.password=123456

3）在所有涉及：service-url.defaultZone的地方修改配置：
eureka.client.service-url.defaultZone = http://admin:123456@sdpeer2:8262/eureka,http://admin:123456@sdpeer3:8263/eureka

4)
注册中心关闭Spring Security的CSRF验证

/**
 1. 如果eurekaServer设置了登录密码   就必须关闭Spring Security的CSRF验证
 */
@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(); //关闭csrf
        super.configure(http);
    }
}


    
    
        
       