package org.aburavov.otus.java.professional.hw12.web.server;

public interface ClientsWebServer {

    void start() throws Exception;

    void join() throws Exception;

    void stop() throws Exception;
}
