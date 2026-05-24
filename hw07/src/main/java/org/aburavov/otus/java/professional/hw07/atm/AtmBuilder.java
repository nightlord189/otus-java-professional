package org.aburavov.otus.java.professional.hw07.atm;

import java.util.EnumMap;
import java.util.Map;

public class AtmBuilder {
    private AtmStatus status = AtmStatus.WORKING;
    private final Map<Nominal, Integer> cash = new EnumMap<>(Nominal.class);

    public AtmBuilder status(AtmStatus status) {
        this.status = status;
        return this;
    }

    public AtmBuilder cell(Nominal nominal, int count) {
        if (count <= 0) {
            throw new IllegalArgumentException("Count must be positive: " + count);
        }
        cash.put(nominal, count);
        return this;
    }

    public Atm build() {
        return new Atm(new AtmState(status, cash));
    }
}
