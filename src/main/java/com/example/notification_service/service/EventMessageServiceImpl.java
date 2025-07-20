package com.example.notification_service.service;

import com.example.notification_service.entity.DiscountCampaign;
import com.example.notification_service.entity.PaymentConfirmation;
import com.example.notification_service.repository.DiscountCampaignRepository;
import com.example.notification_service.repository.PaymentConfirmationRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
public class EventMessageServiceImpl implements EventMessageService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final PaymentConfirmationRepository paymentConfirmationRepository;

    private final DiscountCampaignRepository discountCampaignRepository;

    private static final Logger logger = LoggerFactory.getLogger(EventMessageServiceImpl.class);

    public EventMessageServiceImpl(KafkaTemplate<String, Object> kafkaTemplate,
                                   PaymentConfirmationRepository paymentConfirmationRepository,
                                   DiscountCampaignRepository discountCampaignRepository) {
        this.kafkaTemplate = kafkaTemplate;
        this.paymentConfirmationRepository = paymentConfirmationRepository;
        this.discountCampaignRepository = discountCampaignRepository;
    }

    @Override
    @Transactional
    public void savePaymentConfirmation(PaymentConfirmation paymentConfirmation) {
        paymentConfirmationRepository.save(paymentConfirmation);

        TransactionSynchronizationManager.registerSynchronization(
                new TransactionSynchronization() {

                    @Override
                    public void afterCommit() {
                        kafkaTemplate.send("payment-confirmation-events", paymentConfirmation);
                        logger.info("PaymentConfirmation confirmation event sent successfully");
                    }

                    @Override
                    public void afterCompletion(int status) {
                        if (status != TransactionSynchronization.STATUS_COMMITTED) {
                            logger.warn("Transaction rolled back, skipping Kafka send for: {}", paymentConfirmation);
                        }
                    }
                });
    }

    public void saveDiscountCampaign(DiscountCampaign discountCampaign) {
        // It will be batched or delayed
        discountCampaignRepository.save(discountCampaign);
        kafkaTemplate.send("discount-campaign-events", discountCampaign);
        logger.info("DiscountCampaign confirmation event sent successfully");
    }

}
