package org.aburavov.otus.java.professional.hw14.crm.model;

import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

@Table("client")
public record Client(
        @Id Long id,
        String name,
        @MappedCollection(idColumn = "client_id") Address address,
        @MappedCollection(idColumn = "client_id") Set<Phone> phones) {}
