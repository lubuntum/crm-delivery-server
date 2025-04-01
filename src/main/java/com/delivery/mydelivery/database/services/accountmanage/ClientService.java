package com.delivery.mydelivery.database.services.accountmanage;

import com.delivery.mydelivery.database.repositories.accountmanage.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;
}
