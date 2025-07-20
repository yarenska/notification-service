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

    private final List<PaymentConfirmation> consumedPaymentConfirmationConfirmations = new LinkedList<>();
    private final List<DiscountCampaign> consumedDiscountCampaigns = new LinkedList<>();

    @KafkaListener(topics = "payment-confirmation-events", groupId = "transaction-group")
    public void consumePaymentConfirmationEvents(ConsumerRecord<String, PaymentConfirmation> record, Acknowledgment ack) {
        PaymentConfirmation paymentConfirmation = record.value();
        consumedPaymentConfirmationConfirmations.add(paymentConfirmation);
        ack.acknowledge(); // manual ack
    }

    @KafkaListener(topics = "discount-campaign-events", groupId = "marketing-group")
    public void consumeDiscountCampaignEvents(ConsumerRecord<String, DiscountCampaign> record, Acknowledgment ack) {
        DiscountCampaign discountCampaign = record.value();
        consumedDiscountCampaigns.add(discountCampaign);
        ack.acknowledge(); // manual ack
    }

    public List<PaymentConfirmation> getAllPaymentConfirmationEvents () {
        return Collections.unmodifiableList(consumedPaymentConfirmationConfirmations);
    }

    public List<DiscountCampaign> getAllDiscountCampaignEvents () {
        return Collections.unmodifiableList(consumedDiscountCampaigns);
    }
}
