package com.delivery.mydelivery.services.smsprovider.smsaero;

import com.delivery.mydelivery.database.entities.accountmanage.Client;
import com.delivery.mydelivery.database.entities.ordermanage.ClientOrder;
import com.delivery.mydelivery.dto.ordermanage.clientorder.ClientOrderDTO;
import com.delivery.mydelivery.services.smsprovider.SmsBalance;
import com.delivery.mydelivery.services.smsprovider.SmsProvider;
import com.delivery.mydelivery.services.smsprovider.SmsRequest;
import com.delivery.mydelivery.services.smsprovider.SmsResponse;
import com.delivery.mydelivery.services.smsprovider.smsaero.configuration.SmsAeroConfig;
import com.delivery.mydelivery.services.smsprovider.smsaero.dto.SmsAeroRequest;
import com.delivery.mydelivery.services.smsprovider.smsaero.dto.SmsAeroResponse;
import lombok.AllArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.smsaero.SmsAero;
import org.json.simple.JSONObject;
import org.json.simple.*;
import java.net.URI;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class SmsAeroService implements SmsProvider {
    private static final String SMS_SEND_ENDPOINT = "/sms/send";
    private static final String BALANCE_ENDPOINT = "/balance";
    private static final String CHANNEL_INTERNATIONAL = "INTERNATIONAL";
    private static final String CHANNEL_DIRECT = "DIRECT";
    private final SmsAeroConfig smsAeroConfig;
    private final RestTemplate restTemplate;
    @Override
    public String getProviderName() {
        return "SMS_AERO";
    }

    @Override
    public SmsResponse sendSms(SmsRequest request) {
        return null;
    }

    @Override
    public SmsResponse sendBulkSms(List<ClientOrder> clientOrders, String messageTemplate) {
        SmsAero smsClient = new SmsAero(smsAeroConfig.getEmail(), smsAeroConfig.getApiKey());
        DateTimeFormatter formatter  = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        try {
            for (ClientOrder order : clientOrders) {
                String message = messageTemplate
                        .replaceAll("name", order.getClient().getName())
                        .replace("created_at", formatter.format(order.getCreatedAt()))
                        .replace("company", order.getOrganization().getName());
                JSONObject jsonObject = smsClient.SendSms(order.getClient().getPhone(), message);
                System.out.println(jsonObject.toJSONString());
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    @Override
    public SmsBalance checkBalance() {
        return null;
    }
    private String formatPhoneNumber(String phoneNumber) {
        String digits = phoneNumber.replaceAll("\\D+", "");
        if (digits.startsWith("8") && digits.length() == 1)
            return "+7" + digits.substring(1);
        else if (digits.startsWith("7") && digits.length() == 11)
            return "+" + digits;
        else if (!digits.startsWith("+") && digits.length() == 10)
            return "+7" + digits;
        return "+" + digits;
    }
}
