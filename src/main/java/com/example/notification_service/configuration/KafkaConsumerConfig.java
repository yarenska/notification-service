package com.example.notification_service.configuration;

import com.example.notification_service.entity.PaymentConfirmation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
public class KafkaConsumerConfig {

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory(
            DefaultKafkaConsumerFactory<String, Object> kafkaConsumerFactory) {

        final ConcurrentKafkaListenerContainerFactory<String, Object> containerFactory
                = new ConcurrentKafkaListenerContainerFactory<>();
        containerFactory.setConsumerFactory(kafkaConsumerFactory);
        containerFactory.setConcurrency(3); // number of concurrent consumers
        containerFactory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL); // manual ack

        containerFactory.setCommonErrorHandler(new DefaultErrorHandler(
                new FixedBackOff(1000L, 3L) // retry 3 times with 1s delay
        ));

        return containerFactory;
    }
}
