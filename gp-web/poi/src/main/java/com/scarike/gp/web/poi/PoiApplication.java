package com.scarike.gp.web.poi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class PoiApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(PoiApplication.class, args);
    }
}
