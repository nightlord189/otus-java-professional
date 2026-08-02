package org.aburavov.otus.java.professional.hw14.crm.repository;

import org.aburavov.otus.java.professional.hw14.crm.model.Client;
import org.springframework.data.repository.ListCrudRepository;

public interface ClientRepository extends ListCrudRepository<Client, Long> {}
