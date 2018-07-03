package com.example.controller;

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
public class SwaggerConfig {
	
	private static final String SWAGGER_API_VERSION = "1.0";
	private static final String LICENCE_TEXT = "Apache Licence Version 2.0";
	private static final String title = "Customer REST API";
	private static final String description = "RESTful API for Customers";
	
	
	private ApiInfo apiInfo() {
		
		return new ApiInfoBuilder()
				.title(title)
				.description(description)
				.license(LICENCE_TEXT)
				.version(SWAGGER_API_VERSION)
				.build();
	}
	
    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
        		.apiInfo(apiInfo())
        		.pathMapping("/root-path")
                .select()                 
                .apis(RequestHandlerSelectors.basePackage("com.example.controller"))
                .paths(PathSelectors.regex("/api.*"))
                .build();
    }
}