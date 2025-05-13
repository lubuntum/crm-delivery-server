package com.delivery.mydelivery.database.services.orderimage;

import com.delivery.mydelivery.database.entities.orderimages.OrderImage;
import com.delivery.mydelivery.database.repositories.orderimages.OrderImageRepository;
import com.delivery.mydelivery.utility.file.FIleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Service
public class OrderImageService {
    @Autowired
    OrderImageRepository orderImageRepository;
    @Value("${images.folder}")
    private String imageFolder;
    public List<OrderImage> saveImages(List<MultipartFile> images) {
        if (images == null) return null;
        List<OrderImage> orderImages = new LinkedList<>();
        images.forEach(image -> {
            try {
                String path = FIleUtil.saveFileToDir(image, imageFolder, true);
                OrderImage orderImage = new OrderImage();
                orderImage.setPath(path);
                orderImages.add(orderImageRepository.save(orderImage));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        return orderImages;
    }
}
