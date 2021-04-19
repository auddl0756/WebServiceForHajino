package com.roon.board;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

//@SpringBootApplication
//Indicates a configuration class that declares one or more @Bean methods
// and also triggers auto-configuration and component scanning.
// This is a convenience annotation that is equivalent to declaring
// @Configuration,
// @EnableAutoConfiguration and
// @ComponentScan.

@EnableJpaAuditing    //Annotation to enable auditing in JPA via annotation configuration
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}
