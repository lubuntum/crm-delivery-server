package com.delivery.mydelivery.dto.ordermanage;

import com.delivery.mydelivery.database.entities.accountmanage.Client;
import com.delivery.mydelivery.database.entities.ordermanage.status.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientOrderDTO {
    private Long id;
    private String serialNumber;
    private String address;
    private String comment;
    private LocalDateTime createdAt;
    private StatusEnum status;
    private BigDecimal totalPrice;
    private Long clientId;
    private String clientName;
    private String clientSecondName;
    private String clientPatronymic;
    private String clientEmail;
    private String clientPhone;
    private Long organizationId;

    public Client extractClientData(){
        Client client = new Client();
        client.setName(clientName);
        client.setSecondName(clientSecondName);
        client.setPatronymic(clientPatronymic);
        client.setEmail(clientEmail);
        client.setPhone(clientPhone);
        return client;
    }
    @Override
    public String toString(){
        return String.format("📦 **Новая заявка на доставку!**\n\n" +
                "👤 **ФИО:** %s\n" +
                "🏠 **Адрес доставки:** %s\n" +
                "📞 **Номер телефона:** %s\n" +
                "📝 **Комментарий диспетчера:** %s\n\n" +
                "Пожалуйста, обработайте заявку как можно скорее!",
                String.format("%s %s %s", clientSecondName, clientName, clientPatronymic),
                address,
                clientPhone,
                comment != null ? comment : "Нет комментария");
    }
}
