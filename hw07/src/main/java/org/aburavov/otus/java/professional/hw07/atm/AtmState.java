package org.aburavov.otus.java.professional.hw07.atm;

import java.util.EnumMap;
import java.util.Map;

public class AtmState {
    private final AtmStatus status;
    private final Map<Nominal, Integer> cash;

    public AtmState(AtmStatus status, Map<Nominal, Integer> cash) {
        this.status = status;
        Map<Nominal, Integer> copy = new EnumMap<>(Nominal.class);
        copy.putAll(cash);
        this.cash = copy;
    }

    public AtmState(AtmState other) {
        this(other.status, other.cash);
    }

    public AtmStatus getStatus() {
        return status;
    }

    public Map<Nominal, Integer> getCash() {
        return cash;
    }
}
