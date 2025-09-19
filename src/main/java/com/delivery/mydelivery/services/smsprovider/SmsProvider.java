package com.delivery.mydelivery.services.smsprovider;

import com.delivery.mydelivery.database.entities.accountmanage.Client;
import com.delivery.mydelivery.database.entities.ordermanage.ClientOrder;
import com.delivery.mydelivery.dto.ordermanage.clientorder.ClientOrderDTO;

import java.math.BigDecimal;
import java.util.List;

public interface SmsProvider {
    String getProviderName();
    SmsResponse sendSms(SmsRequest smsRequest);
    SmsResponse sendBulkSms(List<ClientOrder> clientOrders, String messageTemplate);
    SmsBalance checkBalance();
}

