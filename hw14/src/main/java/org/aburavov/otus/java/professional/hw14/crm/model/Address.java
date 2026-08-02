package org.aburavov.otus.java.professional.hw14.crm.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("address")
public record Address(@Id Long id, String street) {}
