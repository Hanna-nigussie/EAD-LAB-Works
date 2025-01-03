
// Hanna Nigussie
//UGR/9180/14



package com.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@ServletComponentScan("com.labexam")
@Configuration("applicationContext.xml")
@ComponentScan("com.labexam")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
