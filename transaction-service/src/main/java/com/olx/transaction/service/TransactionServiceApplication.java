package com.olx.transaction.service;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class TransactionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransactionServiceApplication.class, args);
	}
	
	@Bean
    public Docket swaggerConiguration() {
	return new Docket(DocumentationType.SWAGGER_2).select()
												  .apis(RequestHandlerSelectors.basePackage("com.olx.transaction.service"))
												  .paths(PathSelectors.any())
												  .build()
												  .apiInfo(new ApiInfo(
															"Transaction Microservice API",
															"API for Transaction Microservice",
															"1.0",
															"Free to use",
															new springfox.documentation.service.Contact("Fatih Zukorlic","http://javabrains.io","fzukorlic1@etf.unsa.ba"),
															"API License",
															"http://javabrains.io/",
															Collections.emptyList()));
	}

	@Bean
	@LoadBalanced
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
}