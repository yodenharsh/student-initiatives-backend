package com.woxsen.studentinitiatives;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.woxsen.studentinitiatives.storage.ImageProperties;

@SpringBootApplication
@EnableConfigurationProperties(ImageProperties.class)
public class StudentInitiativesBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentInitiativesBackendApplication.class, args);
	}

}
