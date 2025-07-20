package com.example.notification_service.configuration;

import com.example.notification_service.entity.PaymentConfirmation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;

// I defined the basic configuration in application.yaml file but kept this file also, because I may define additional custom logic
//  like retry templates, dead-letter handling, manual acknowlegement etc.
@Configuration
public class KafkaProducerConfig {

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate(
            DefaultKafkaProducerFactory<String, Object> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }
}
