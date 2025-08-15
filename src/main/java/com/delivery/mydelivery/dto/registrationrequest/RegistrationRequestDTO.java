package com.delivery.mydelivery.dto.registrationrequest;

import com.delivery.mydelivery.database.entities.registrationrequest.RequestStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequestDTO {
    private Long id;
    private RequestStatusEnum requestStatus;
}
