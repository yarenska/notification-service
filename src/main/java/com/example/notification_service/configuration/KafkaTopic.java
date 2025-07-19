package com.example.notification_service.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopic {

    @Bean
    public NewTopic newTopic() {
        return TopicBuilder.name("custom-events")
                .replicas(1) //For simplicity and non-production setup
                .partitions(3) //Since I configured to concurrency to 3, each consumer thread will read from one 1 partition
                .config("retention.ms", "86400000") // 1 day
                .config("cleanup.policy", "delete") // or "compact"
                .build();
    }
}
