package com.delivery.mydelivery.database.services.ordermanage.payment;

import com.delivery.mydelivery.database.entities.ordermanage.payment.PaymentMethod;
import com.delivery.mydelivery.database.entities.ordermanage.payment.PaymentMethodEnum;
import com.delivery.mydelivery.database.repositories.ordermanage.payment.PaymentMethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentMethodService {
    @Autowired
    PaymentMethodRepository paymentMethodRepository;

    public PaymentMethod getPaymentMethodByName(PaymentMethodEnum name) {
        return paymentMethodRepository.findPaymentMethodByName(name);
    }
}
