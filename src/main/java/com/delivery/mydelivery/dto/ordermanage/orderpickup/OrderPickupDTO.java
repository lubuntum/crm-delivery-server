package com.delivery.mydelivery.dto.ordermanage.orderpickup;

import com.delivery.mydelivery.database.entities.orderimages.OrderImage;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderPickupDTO {
    private Long id;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime takenAt;
    private String comment;
    private Integer itemsCount;
    private Long orderId;
    private Long courierId;
    private List<OrderImage> orderImages;
    private List<MultipartFile> imagesTemp;
}
