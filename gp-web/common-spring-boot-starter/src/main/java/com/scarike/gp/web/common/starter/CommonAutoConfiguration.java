package com.scarike.gp.web.common.starter;

import com.scarike.gp.web.common.starter.controller.GlobalControllerExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonAutoConfiguration {
    @Bean
    public GlobalControllerExceptionHandler globalControllerExceptionHandler(){
        return new GlobalControllerExceptionHandler();
    }
}
