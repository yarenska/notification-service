package com.example.notification_service.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/events")
public class EventMessageController {

    private final KafkaTemplate<String, EventMessage> kafkaTemplate;

    private static final Logger logger = LoggerFactory.getLogger(EventMessageController.class);

    public EventMessageController(KafkaTemplate<String, EventMessage> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping
    public ResponseEntity<String> sendEventMessage(@RequestBody EventMessage eventMessage) {
        try {
            kafkaTemplate.send("custom-events", eventMessage);
            logger.info("Event sent successfully");
            return ResponseEntity.ok("Event sent successfully");
        } catch (Exception e) {
            logger.error("Failed to sent message", e);
            return ResponseEntity.badRequest().body("Event sending failed");
        }
    }
}
