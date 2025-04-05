package com.delivery.mydelivery.dto.ordermanage;

import com.delivery.mydelivery.database.entities.accountmanage.Client;
import com.delivery.mydelivery.database.entities.ordermanage.status.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientOrderDTO {
    private Long id;
    private String address;
    private String comment;
    private LocalDateTime createdAt;
    private StatusEnum status;
    private Long clientId;
    private String clientName;
    private String clientSecondName;
    private String clientPatronymic;
    private String clientEmail;
    private String clientPhone;

    public Client extractClientData(){
        Client client = new Client();
        client.setName(clientName);
        client.setSecondName(clientSecondName);
        client.setPatronymic(clientPatronymic);
        client.setEmail(clientEmail);
        client.setPhone(clientPhone);
        return client;
    }
}
