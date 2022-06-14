package com.aholg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class App {

    public static void main(String[] args) {
        String host = "localhost:8080";
        SpringApplication.run(App.class, args);
    }
}

