package com.delivery.mydelivery.dto.productmanage;

import com.delivery.mydelivery.database.entities.orderimages.OrderImage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTO {
    Long id;
    Long materialId;
    String materialName;
    Long orderId;
    BigDecimal price;
    BigDecimal pricePerUnit;
    BigDecimal additionalPrice;
    Double size;
    Double width;
    Double height;
    String comment;
    Boolean isReady;
    List<OrderImage> orderImages;
    List<MultipartFile> imagesTemp;
}
