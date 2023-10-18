package com.hoop.api;

import com.hoop.api.config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(AppConfig.class)
@SpringBootApplication
public class HoopApplication {

    public static void main(String[] args) {
        SpringApplication.run(HoopApplication.class, args);
    }

}
