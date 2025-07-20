package com.example.notification_service.controller;

import com.example.notification_service.entity.DiscountCampaign;
import com.example.notification_service.entity.PaymentConfirmation;
import com.example.notification_service.entity.EventMessageConsumer;
import com.example.notification_service.service.EventMessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/events")
public class EventMessageController {

    private final EventMessageConsumer consumer;

    private final EventMessageService eventMessageService;

    public EventMessageController(EventMessageConsumer consumer,
                                  EventMessageService eventMessageService) {
        this.consumer = consumer;
        this.eventMessageService = eventMessageService;
    }

    @PostMapping("/payment-confirmation")
    public ResponseEntity<String> sendPaymentEventMessage(@RequestBody PaymentConfirmation paymentConfirmation) {
        try {
            eventMessageService.savePaymentConfirmation(paymentConfirmation);
            return ResponseEntity.ok("PaymentConfirmation confirmation event sent successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Event sending failed");
        }
    }

    @PostMapping("/discount-campaign")
    public ResponseEntity<String> sendDiscountCampaignEventMessage(@RequestBody DiscountCampaign discountCampaign) {
        try {
            eventMessageService.saveDiscountCampaign(discountCampaign);
            return ResponseEntity.ok("Discount campaign event sent successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Event sending failed");
        }
    }

    @GetMapping(value = "/payment-confirmation")
    public void consumePayment() {
        PaymentConfirmation paymentConfirmation = consumer.getAllPaymentConfirmationEvents().get(0);
        System.out.println("Consumed message: " + paymentConfirmation.toString());
    }

    @GetMapping("/discount-campaign")
    public void consumeDiscount() {
        DiscountCampaign discountCampaign = consumer.getAllDiscountCampaignEvents().get(0);
        System.out.println("Consumed message: " + discountCampaign.toString());
    }
}
