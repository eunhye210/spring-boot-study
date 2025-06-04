package com.eunhye.onus_crud_3.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    @Bean(name = "emailExecutor")
    public Executor emailExecutor() {
        return Executors.newSingleThreadExecutor();
    }

}
