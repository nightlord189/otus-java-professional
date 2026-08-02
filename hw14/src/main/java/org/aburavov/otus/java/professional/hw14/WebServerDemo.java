package org.aburavov.otus.java.professional.hw14;

import org.aburavov.otus.java.professional.hw14.core.repository.DataTemplateHibernate;
import org.aburavov.otus.java.professional.hw14.core.repository.HibernateUtils;
import org.aburavov.otus.java.professional.hw14.core.sessionmanager.TransactionManagerHibernate;
import org.aburavov.otus.java.professional.hw14.crm.model.Address;
import org.aburavov.otus.java.professional.hw14.crm.model.Client;
import org.aburavov.otus.java.professional.hw14.crm.model.Phone;
import org.aburavov.otus.java.professional.hw14.crm.service.DbServiceClientImpl;
import org.aburavov.otus.java.professional.hw14.web.server.ClientsWebServerImpl;
import org.aburavov.otus.java.professional.hw14.web.services.InMemoryUserAuthService;
import org.aburavov.otus.java.professional.hw14.web.services.TemplateProcessorImpl;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class WebServerDemo {

    private static final Logger log = LoggerFactory.getLogger(WebServerDemo.class);

    public static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";
    public static final String TEMPLATES_DIR = "/templates/";
    public static final int WEB_SERVER_PORT = 8080;

    public static void main(String[] args) throws Exception {
        var configuration = new Configuration().configure(HIBERNATE_CFG_FILE);

        var sessionFactory = HibernateUtils.buildSessionFactory(configuration, Client.class, Address.class, Phone.class);

        var transactionManager = new TransactionManagerHibernate(sessionFactory);
        var clientTemplate = new DataTemplateHibernate<>(Client.class);
        var dbServiceClient = new DbServiceClientImpl(transactionManager, clientTemplate);

        dbServiceClient.saveClient(new Client(
                null,
                "Ivan",
                new Address("Pushkina"),
                List.of(new Phone("101"), new Phone("102"))));

        var templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);
        var userAuthService = new InMemoryUserAuthService(Map.of("admin", "admin"));

        var webServer = new ClientsWebServerImpl(WEB_SERVER_PORT, dbServiceClient, userAuthService, templateProcessor);
        webServer.start();
        log.info("Web server started on http://localhost:{}", WEB_SERVER_PORT);
        webServer.join();
    }
}
