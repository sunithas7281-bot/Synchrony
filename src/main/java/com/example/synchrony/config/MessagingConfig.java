package com.example.synchrony.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class MessagingConfig {
    @Bean
    public NewTopic imageTopic(@Value("${app.kafka.topic:image-uploads}") String topic) {
        return TopicBuilder.name(topic).partitions(3).replicas(1).build();
    }
}
