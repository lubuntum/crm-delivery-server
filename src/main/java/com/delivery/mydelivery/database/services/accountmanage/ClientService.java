package com.delivery.mydelivery.database.services.accountmanage;

import com.delivery.mydelivery.database.entities.accountmanage.Client;
import com.delivery.mydelivery.database.repositories.accountmanage.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;
    public Client getClientById(Long id) {
        return clientRepository.findById(id).orElseThrow(NullPointerException::new);
    }
    public Long createClientIfNotExists(Client client) {
        Client clientFromDb = clientRepository.findByPhone(client.getPhone());
        if (clientFromDb != null) return clientFromDb.getId();
        return clientRepository.save(client).getId();
    }
}
