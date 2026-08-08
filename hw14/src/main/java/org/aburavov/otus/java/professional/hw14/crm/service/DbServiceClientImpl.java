package org.aburavov.otus.java.professional.hw14.crm.service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.aburavov.otus.java.professional.hw14.crm.model.Client;
import org.aburavov.otus.java.professional.hw14.crm.repository.ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DbServiceClientImpl implements DBServiceClient {

    private static final Logger log = LoggerFactory.getLogger(DbServiceClientImpl.class);

    private final ClientRepository clientRepository;

    public DbServiceClientImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client saveClient(Client client) {
        var savedClient = clientRepository.save(client);
        log.info("saved client: {}", savedClient);
        return savedClient;
    }

    @Override
    public Optional<Client> getClient(long id) {
        return clientRepository.findById(id);
    }

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll().stream()
                .sorted(Comparator.comparing(Client::id))
                .toList();
    }
}
