package com.cst438;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(RsaKeyProperties.class)
@SpringBootApplication(exclude = { 
    org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class, 
    org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration.class 
})
public class RegistrarMain {

	public static void main(String[] args) {
		SpringApplication.run(RegistrarMain.class, args);
	}

}
