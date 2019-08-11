package com.jfrog.ArtifactoryRest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
public class Application extends SpringBootServletInitializer{
	
	/*
	 * Sring Boot Application starts
	 */
	 public static void main(String[] args) {

	      SpringApplication.run(Application.class, args);
	   }
	 
	 @Override
	    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
	        return application.sources(Application.class);
	    }
	 
	 /*
	  * Create Sprint RestTemplate Bean
	  * to consume Rest API
	  */
	 @Bean
	   public RestTemplate getRestTemplate() {
	      return new RestTemplate();
	   }

}
