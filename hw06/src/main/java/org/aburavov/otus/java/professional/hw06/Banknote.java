package org.aburavov.otus.java.professional.hw06;

public record Banknote(Currency currency, long nominal) {
    public Banknote {
        if (nominal <= 0) {
            throw new IllegalArgumentException("Nominal must be positive");
        }
    }
}
