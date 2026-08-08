package org.aburavov.otus.java.professional.hw14.crm.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("phone")
public record Phone(@Id Long id, String number) {}
