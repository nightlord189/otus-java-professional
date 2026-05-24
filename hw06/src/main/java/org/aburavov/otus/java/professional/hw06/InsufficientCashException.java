package org.aburavov.otus.java.professional.hw06;

public class InsufficientCashException extends RuntimeException {
    public InsufficientCashException(Currency currency, long amount) {
        super("Cannot withdraw "+amount+" "+currency);
    }
}
