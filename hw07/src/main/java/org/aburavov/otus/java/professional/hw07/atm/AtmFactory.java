package org.aburavov.otus.java.professional.hw07.atm;

import java.util.Map;

public final class AtmFactory {

    private AtmFactory() {
    }

    public static Atm createEmpty() {
        return new AtmBuilder().build();
    }

    public static Atm createWithCash(Map<Nominal, Integer> cash) {
        AtmBuilder builder = new AtmBuilder();
        cash.forEach(builder::cell);
        return builder.build();
    }

    public static Atm createStandard() {
        AtmBuilder builder = new AtmBuilder();
        for (Nominal nominal : Nominal.values()) {
            builder.cell(nominal, 10);
        }
        return builder.build();
    }
}
