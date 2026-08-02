package org.aburavov.otus.java.professional.hw12;

import org.aburavov.otus.java.professional.hw12.core.repository.DataTemplateHibernate;
import org.aburavov.otus.java.professional.hw12.core.repository.HibernateUtils;
import org.aburavov.otus.java.professional.hw12.core.sessionmanager.TransactionManagerHibernate;
import org.aburavov.otus.java.professional.hw12.crm.model.Address;
import org.aburavov.otus.java.professional.hw12.crm.model.Client;
import org.aburavov.otus.java.professional.hw12.crm.model.Phone;
import org.aburavov.otus.java.professional.hw12.crm.service.DbServiceClientImpl;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class DbServiceDemo {

    private static final Logger log = LoggerFactory.getLogger(DbServiceDemo.class);

    public static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";

    public static void main(String[] args) {
        var configuration = new Configuration().configure(HIBERNATE_CFG_FILE);

        var sessionFactory = HibernateUtils.buildSessionFactory(configuration, Client.class, Address.class, Phone.class);

        var transactionManager = new TransactionManagerHibernate(sessionFactory);
        var clientTemplate = new DataTemplateHibernate<>(Client.class);
        var dbServiceClient = new DbServiceClientImpl(transactionManager, clientTemplate);

        var firstClient = new Client(
                null,
                "Ivan",
                new Address("Pushkina"),
                List.of(new Phone("101"), new Phone("102")));
        dbServiceClient.saveClient(firstClient);


        var clientSecond = dbServiceClient.saveClient(new Client(
                null,
                "Alexander",
                new Address("Abaya"),
                List.of(new Phone("201"))));

        var clientSecondSelected = dbServiceClient
                .getClient(clientSecond.getId())
                .orElseThrow(() -> new RuntimeException("Client not found, id:" + clientSecond.getId()));
        log.info("clientSecondSelected:{}", clientSecondSelected);

        dbServiceClient.saveClient(new Client(clientSecondSelected.getId(), "dbServiceSecondUpdated"));
        var clientUpdated = dbServiceClient
                .getClient(clientSecondSelected.getId())
                .orElseThrow(() -> new RuntimeException("Client not found, id:" + clientSecondSelected.getId()));
        log.info("clientUpdated:{}", clientUpdated);

        log.info("All clients");
        dbServiceClient.findAll().forEach(client -> log.info("client:{}", client));
    }
}
