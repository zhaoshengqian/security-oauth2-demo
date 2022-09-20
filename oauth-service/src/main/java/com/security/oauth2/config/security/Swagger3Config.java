package com.security.oauth2.config.security;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
// 原来的@EnableSwagger2去掉

public class Swagger3Config {

    @Bean
    public Docket createRestApi() {

//        log.info("加载Swagger3");

        return new Docket(DocumentationType.OAS_30) // 注意此处改动，需要将SWAGGER_2改成OAS_30
                .apiInfo(apiInfo()).select()
                // 扫描所有有注解的api，用这种方式更灵活
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("swagger3接口文档")
                .description("接口文档")
                .contact(new Contact("碧海燕鱼", "#", "654195681@qq.com"))
                .version("0.1.0")
                .build();
    }
}
