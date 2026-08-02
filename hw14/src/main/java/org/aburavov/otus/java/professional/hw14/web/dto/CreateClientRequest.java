package org.aburavov.otus.java.professional.hw14.web.dto;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.aburavov.otus.java.professional.hw14.crm.model.Address;
import org.aburavov.otus.java.professional.hw14.crm.model.Client;
import org.aburavov.otus.java.professional.hw14.crm.model.Phone;

public record CreateClientRequest(String name, String address, String phones) {

    public boolean hasName() {
        return name != null && !name.isBlank();
    }

    public Client toClient() {
        return new Client(null, name.trim(), parseAddress(), parsePhones());
    }

    private Address parseAddress() {
        return address == null || address.isBlank() ? null : new Address(null, address.trim());
    }

    private Set<Phone> parsePhones() {
        if (phones == null || phones.isBlank()) {
            return Set.of();
        }
        return Arrays.stream(phones.split(","))
                .map(String::trim)
                .filter(number -> !number.isEmpty())
                .map(number -> new Phone(null, number))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
