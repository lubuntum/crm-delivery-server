package com.delivery.mydelivery.database.services.ordermanage;

import com.delivery.mydelivery.database.repositories.ordermanage.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatusService {
    @Autowired
    StatusRepository statusRepository;
}
