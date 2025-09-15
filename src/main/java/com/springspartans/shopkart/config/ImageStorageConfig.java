package com.springspartans.shopkart.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ImageStorageConfig {

	@Value("${file.project-path}")
	private String projectPath;
	
	@Value("${file.image-path}")
	private String imagePath;
	
	private String uploadPath;

    @Bean
    String getUploadPath() {
		uploadPath = projectPath + imagePath;
		return uploadPath;
	}
    
}
