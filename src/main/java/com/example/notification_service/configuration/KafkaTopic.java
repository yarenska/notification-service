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
                .replicas(3)
                .partitions(1)
                .config("retention.ms", "86400000") // 1 day
                .config("cleanup.policy", "delete") // or "compact"
                .build();
    }
}
