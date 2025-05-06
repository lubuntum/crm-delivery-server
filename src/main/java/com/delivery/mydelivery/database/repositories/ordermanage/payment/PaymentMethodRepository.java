package com.delivery.mydelivery.database.repositories.ordermanage.payment;

import com.delivery.mydelivery.database.entities.ordermanage.payment.PaymentMethod;
import com.delivery.mydelivery.database.entities.ordermanage.payment.PaymentMethodEnum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {
    PaymentMethod findPaymentMethodByName(PaymentMethodEnum name);
}
