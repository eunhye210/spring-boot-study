package com.eunhye.onus_crud_3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EnableCaching
public class OnusCrud3Application {

    public static void main(String[] args) {
        SpringApplication.run(OnusCrud3Application.class, args);
    }

}
