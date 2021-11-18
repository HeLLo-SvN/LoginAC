package com.accenture.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 访问Swagger2入口地址
 * 官方UI入口地址      http://localhost:8088/swagger-ui.html
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * Swagger2核心配置
     * 配置 docket
     * @return 返回一个Docket类型对象交给@Bean注解处理
     */
    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors
                        .basePackage("com.accenture.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     *为createRestApi()中的.apiInfo(apiInfo())返回一个api文档汇总信息
     * @return 返回一个ApiInfo类型对象
     */
    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("周二布置的关于用户登录+Redis缓存Token的任务")
                .contact(new Contact(
                        "孙际航",
                        "",
                        "jihang.sun@accenture.com"))
                .description("我用的是拦截器拦截校验Token的方式实现，实现Redis的分布式会话登录")
                .version("1.0.0")
                .termsOfServiceUrl("")
                .build();
    }

}
