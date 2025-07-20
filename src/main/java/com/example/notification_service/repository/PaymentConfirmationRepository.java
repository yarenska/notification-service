package com.example.notification_service.repository;

import com.example.notification_service.entity.PaymentConfirmation;
import org.springframework.data.repository.CrudRepository;

public interface PaymentConfirmationRepository extends CrudRepository<PaymentConfirmation, Integer> {}
