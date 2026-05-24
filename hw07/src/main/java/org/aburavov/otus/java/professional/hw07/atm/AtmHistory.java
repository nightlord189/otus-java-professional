package org.aburavov.otus.java.professional.hw07.atm;

public interface AtmHistory {
    void save(AtmState state);

    void resetTo(int index);
}
