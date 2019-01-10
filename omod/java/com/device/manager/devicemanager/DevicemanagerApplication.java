package com.device.manager.devicemanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.device.manager.devicemanager.respository")
@SpringBootApplication
public class DevicemanagerApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(DevicemanagerApplication.class, args);
	}
}
