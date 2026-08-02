package org.aburavov.otus.java.professional.hw14.web.controller;

import org.aburavov.otus.java.professional.hw14.crm.service.DBServiceClient;
import org.aburavov.otus.java.professional.hw14.web.dto.CreateClientRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class ClientsController {

    private final DBServiceClient dbServiceClient;

    public ClientsController(DBServiceClient dbServiceClient) {
        this.dbServiceClient = dbServiceClient;
    }

    @GetMapping({"/", "/clients"})
    public String getClients(Model model) {
        model.addAttribute("clients", dbServiceClient.findAll());
        return "clients";
    }

    @PostMapping("/clients")
    public RedirectView createClient(CreateClientRequest request) {
        if (!request.hasName()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Client name is required");
        }
        dbServiceClient.saveClient(request.toClient());
        return new RedirectView("/clients", true);
    }
}
