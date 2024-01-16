package com.notyficationsystem.NotyficationSystem.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@EnableKafka
public class KafkaTopicConfig {
    @Bean
    public NewTopic notifyEmailTopic(){
        return TopicBuilder.name("email-messages")
                .build();
    }

    @Bean
    public NewTopic notifyTelegramTopic() {
        return TopicBuilder.name("telegram-messages")
                .build();
    }
}
