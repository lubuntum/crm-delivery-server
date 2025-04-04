package com.delivery.mydelivery.dto.ordermanage;

import com.delivery.mydelivery.database.entities.ordermanage.status.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientOrderDTO {
    private Long id;
    private String address;
    private String comment;
    private LocalDate createdAt;
    private StatusEnum status;
    private String clientName;
    private String clientSecondName;
    private String clientPatronymic;
    private String clientEmail;
    private String clientPhone;
}
