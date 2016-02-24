package com.alleywayconsulting.piggybanker.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan
@EnableScheduling
public class PiggyBankerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PiggyBankerApplication.class, args);
    }

}
