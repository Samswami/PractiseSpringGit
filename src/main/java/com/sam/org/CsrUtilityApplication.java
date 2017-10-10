package com.sam.org;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
//import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class CsrUtilityApplication extends  SpringBootServletInitializer {

public static void main(String[] args) {
		SpringApplication.run(CsrUtilityApplication.class, args);
	}
	 @Override
	    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
	        return application.sources(CsrUtilityApplication.class);
	    }

}
