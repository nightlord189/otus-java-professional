package org.aburavov.otus.java.professional.hw14.web.server;

import jakarta.servlet.DispatcherType;
import org.aburavov.otus.java.professional.hw14.crm.service.DBServiceClient;
import org.aburavov.otus.java.professional.hw14.web.services.TemplateProcessor;
import org.aburavov.otus.java.professional.hw14.web.services.UserAuthService;
import org.aburavov.otus.java.professional.hw14.web.servlet.AuthorizationFilter;
import org.aburavov.otus.java.professional.hw14.web.servlet.ClientsServlet;
import org.aburavov.otus.java.professional.hw14.web.servlet.LoginServlet;
import org.eclipse.jetty.ee10.servlet.FilterHolder;
import org.eclipse.jetty.ee10.servlet.ServletContextHandler;
import org.eclipse.jetty.ee10.servlet.ServletHolder;
import org.eclipse.jetty.server.Server;

import java.util.EnumSet;

public class ClientsWebServerImpl implements ClientsWebServer {

    private final Server server;

    public ClientsWebServerImpl(
            int port,
            DBServiceClient dbServiceClient,
            UserAuthService userAuthService,
            TemplateProcessor templateProcessor) {
        server = new Server(port);

        var servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletContextHandler.setContextPath("/");

        var loginServlet = new ServletHolder(new LoginServlet(templateProcessor, userAuthService));
        servletContextHandler.addServlet(loginServlet, "/login");
        servletContextHandler.addServlet(loginServlet, "/");

        servletContextHandler.addServlet(
                new ServletHolder(new ClientsServlet(dbServiceClient, templateProcessor)), "/clients");
        servletContextHandler.addFilter(
                new FilterHolder(new AuthorizationFilter()), "/clients/*", EnumSet.of(DispatcherType.REQUEST));

        server.setHandler(servletContextHandler);
    }

    @Override
    public void start() throws Exception {
        server.start();
    }

    @Override
    public void join() throws Exception {
        server.join();
    }

    @Override
    public void stop() throws Exception {
        server.stop();
    }
}
