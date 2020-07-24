package com.example.LearningLog;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

@SpringBootApplication
public class LearningLogApplication {
    
    public LearningLogApplication(FreeMarkerConfigurer freeMarkerConfigurer) {
        freeMarkerConfigurer
            .getTaglibFactory()
            .setClasspathTlds(Collections.singletonList("/META-INF/security.tld"));
    }
    
	public static void main(String[] args) {
		SpringApplication.run(LearningLogApplication.class, args);
	}
}
