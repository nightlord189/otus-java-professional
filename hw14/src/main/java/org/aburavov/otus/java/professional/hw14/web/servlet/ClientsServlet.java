package org.aburavov.otus.java.professional.hw14.web.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aburavov.otus.java.professional.hw14.crm.model.Address;
import org.aburavov.otus.java.professional.hw14.crm.model.Client;
import org.aburavov.otus.java.professional.hw14.crm.model.Phone;
import org.aburavov.otus.java.professional.hw14.crm.service.DBServiceClient;
import org.aburavov.otus.java.professional.hw14.web.services.TemplateProcessor;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientsServlet extends HttpServlet {

    private static final String CLIENTS_PAGE_TEMPLATE = "clients.html";

    private final transient DBServiceClient dbServiceClient;
    private final transient TemplateProcessor templateProcessor;

    public ClientsServlet(DBServiceClient dbServiceClient, TemplateProcessor templateProcessor) {
        this.dbServiceClient = dbServiceClient;
        this.templateProcessor = templateProcessor;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> data = new HashMap<>();
        data.put("clients", dbServiceClient.findAll());

        response.setContentType("text/html; charset=utf-8");
        response.getWriter().println(templateProcessor.getPage(CLIENTS_PAGE_TEMPLATE, data));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        var name = request.getParameter("name");
        if (name == null || name.isBlank()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Client name is required");
            return;
        }

        dbServiceClient.saveClient(new Client(null, name.trim(), parseAddress(request), parsePhones(request)));
        response.sendRedirect("/clients");
    }

    private Address parseAddress(HttpServletRequest request) {
        var street = request.getParameter("address");
        return street == null || street.isBlank() ? null : new Address(street.trim());
    }

    private List<Phone> parsePhones(HttpServletRequest request) {
        var phones = request.getParameter("phones");
        if (phones == null || phones.isBlank()) {
            return List.of();
        }
        return Arrays.stream(phones.split(","))
                .map(String::trim)
                .filter(number -> !number.isEmpty())
                .map(Phone::new)
                .toList();
    }
}
