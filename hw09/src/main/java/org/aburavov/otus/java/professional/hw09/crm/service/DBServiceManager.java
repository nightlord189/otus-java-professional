package org.aburavov.otus.java.professional.hw09.crm.service;

import java.util.List;
import java.util.Optional;
import org.aburavov.otus.java.professional.hw09.crm.model.Manager;

public interface DBServiceManager {

    Manager saveManager(Manager client);

    Optional<Manager> getManager(long no);

    List<Manager> findAll();
}
