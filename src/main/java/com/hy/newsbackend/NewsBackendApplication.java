package com.hy.newsbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.web.DefaultSecurityFilterChain;

@SpringBootApplication
public class NewsBackendApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(NewsBackendApplication.class, args);
        System.out.println(run.getBean(DefaultSecurityFilterChain.class));
    }

}
