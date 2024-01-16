package com.notyficationsystem.NotyficationSystem;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
public class NotyficationSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(NotyficationSystemApplication.class, args);
    }
}
