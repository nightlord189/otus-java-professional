package org.aburavov.otus.java.professional.hw11;

import org.aburavov.otus.java.professional.hw11.core.repository.DataTemplateHibernate;
import org.aburavov.otus.java.professional.hw11.core.repository.HibernateUtils;
import org.aburavov.otus.java.professional.hw11.core.sessionmanager.TransactionManagerHibernate;
import org.aburavov.otus.java.professional.hw11.crm.model.Address;
import org.aburavov.otus.java.professional.hw11.crm.model.Client;
import org.aburavov.otus.java.professional.hw11.crm.model.Phone;
import org.aburavov.otus.java.professional.hw11.crm.service.DbServiceClientImpl;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class DbServiceDemo {

    private static final Logger log = LoggerFactory.getLogger(DbServiceDemo.class);

    public static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";

    public static void main(String[] args) {
        var configuration = new Configuration().configure(HIBERNATE_CFG_FILE);

        var sessionFactory = HibernateUtils.buildSessionFactory(configuration, Client.class, Address.class, Phone.class);

        var transactionManager = new TransactionManagerHibernate(sessionFactory);
        var clientTemplate = new DataTemplateHibernate<>(Client.class);
        var dbServiceClient = new DbServiceClientImpl(transactionManager, clientTemplate);

        // create a lot of clients until we got id > 127
        Client firstClientSaved = null;
        for (int i = 0; i < 130; i++) {
            var client = new Client(
                    null,
                    "Ivan",
                    new Address("Pushkina"),
                    List.of(new Phone("101"), new Phone("102")));
            firstClientSaved = dbServiceClient.saveClient(client);
        }
        log.info("using client id={} (above the Long-cache range)", firstClientSaved.getId());

        for (int i= 0; i < 5; i++) {
            Long start = System.nanoTime();
            Optional<Client> opt = dbServiceClient.getClient(firstClientSaved.getId());
            Long end = System.nanoTime();
            if (opt.isPresent()) {
                log.info("got client from db: {} with {} nanoseconds", opt.get(), end - start);
            } else {
                log.error("client not found");
                break;
            }
        }

        // put memory under pressure so the GC clears the WeakHashMap-based cache
        log.info("--- applying memory pressure to evict the cache ---");
        try {
            var ballast = new java.util.ArrayList<long[]>();
            while (true) {
                ballast.add(new long[1_000_000]);
            }
        } catch (OutOfMemoryError e) {
            log.info("OutOfMemoryError caught, the cache should be cleared now");
        }
        System.gc();

        // read again: with an empty cache this must hit the database again (slower)
        long start = System.nanoTime();
        Optional<Client> opt = dbServiceClient.getClient(firstClientSaved.getId());
        long end = System.nanoTime();
        log.info("after GC: got client present={} with {} nanoseconds (back from db)", opt.isPresent(), end - start);
    }
}
