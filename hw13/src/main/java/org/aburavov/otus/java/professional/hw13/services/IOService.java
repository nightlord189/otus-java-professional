package org.aburavov.otus.java.professional.hw13.services;

public interface IOService {
    void out(String message);

    String readLn(String prompt);

    int readInt(String prompt);
}
