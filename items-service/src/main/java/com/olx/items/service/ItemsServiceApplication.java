package com.olx.items.service;


import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.client.RestTemplate;


import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class ItemsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ItemsServiceApplication.class, args);
	}
	
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}
	
	@Bean
    public Docket swaggerConiguration() {
	return new Docket(DocumentationType.SWAGGER_2).select()
												  .apis(RequestHandlerSelectors.any())
												  .paths(PathSelectors.any())
												  .build()
												  .apiInfo(new ApiInfo(
															"Items Microservice API",
															"API for Items Microservice",
															"1.0",
															"Free to use",
															new springfox.documentation.service.Contact("Sakib Ademovic","http://javabrains.io","sademovic2@etf.unsa.ba"),
															"API License",
															"http://javabrains.io/",
															Collections.emptyList()));
    }
}
