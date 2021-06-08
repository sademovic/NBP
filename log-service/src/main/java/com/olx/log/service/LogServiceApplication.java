package com.olx.log.service;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class LogServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogServiceApplication.class, args);
	}
	
	@Bean
    public Docket swaggerConiguration() {
	return new Docket(DocumentationType.SWAGGER_2).select()
												  .apis(RequestHandlerSelectors.any())
												  .paths(PathSelectors.any())
												  .build()
												  .apiInfo(new ApiInfo(
															"Log Microservice API",
															"API for Log Microservice",
															"1.0",
															"Free to use",
															new springfox.documentation.service.Contact("Rijad Alilovic","http://javabrains.io","ralilovic1@etf.unsa.ba"),
															"API License",
															"http://javabrains.io/",
															Collections.emptyList()));
	}
}