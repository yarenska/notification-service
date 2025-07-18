package com.example.notification_service.entity;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Component
public class EventMessageConsumer {

    private final List<EventMessage> consumedMessages = new LinkedList<>();

    @KafkaListener(topics = "custom-events", groupId = "events-consumer-group")
    public void consume(ConsumerRecord<String, EventMessage> record, Acknowledgment ack) {
        EventMessage message = record.value();
        consumedMessages.add(message);
        ack.acknowledge(); // manual ack
    }

    public List<EventMessage> getAllMessages() {
        return Collections.unmodifiableList(consumedMessages);
    }
}
