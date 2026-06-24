package org.aburavov.otus.java.professional.hw11.crm.service;

import org.aburavov.otus.java.professional.hw11.crm.model.Client;

import java.util.List;
import java.util.Optional;

public interface DBServiceClient {

    Client saveClient(Client client);

    Optional<Client> getClient(long id);

    List<Client> findAll();
}
