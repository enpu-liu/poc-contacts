package com.orangehouse.poc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig
{
    @Bean
    public Docket createDocket()
    {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(buildApiInfo())
                .select()
                .apis(RequestHandlerSelectors
                        .basePackage("com.orangehouse.poc.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo buildApiInfo()
    {
        return new ApiInfoBuilder()
                .title("POC Contact")
                .description("POC of Spring Boot RESTful API")
                .version("1.0")
                .build();
    }
}