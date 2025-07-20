package com.example.notification_service.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

// As an example I decided to implement a real life problem. For that I have 2 topics as paymentConfirmationTopic
// and discountCampaignTopic
@Configuration
public class KafkaTopic {

    @Bean
    public NewTopic paymentConfirmationTopic () {

        //Creating transactional topic
        return TopicBuilder.name("payment-confirmation-events")
                .replicas(1)
                .partitions(6)
                .config("retention.ms", "86400000") // 1 day
                .config("cleanup.policy", "delete") // or "compact"
                .build();
    }

    //Creating marketing topic
    @Bean
    public NewTopic discountCampaignTopic () {
        return TopicBuilder.name("discount-campaign-events")
                .replicas(1)
                .partitions(4)
                .config("retention.ms", "86400000") // 1 day
                .config("cleanup.policy", "delete") // or "compact"
                .build();
    }
}
