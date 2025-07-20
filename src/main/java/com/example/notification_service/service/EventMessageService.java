package com.example.notification_service.service;

import com.example.notification_service.entity.DiscountCampaign;
import com.example.notification_service.entity.PaymentConfirmation;

public interface EventMessageService {

    void savePaymentConfirmation(PaymentConfirmation paymentConfirmation);

    void saveDiscountCampaign(DiscountCampaign discountCampaign);
}
