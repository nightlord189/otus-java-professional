package org.aburavov.otus.java.professional.hw06;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        System.out.println("HW06");

        Map<Banknote, Integer> cash = new HashMap<>(Map.of(
                new Banknote(Currency.KZT, 1000), 10,
                new Banknote(Currency.KZT, 5000), 4,
                new Banknote(Currency.USD, 100), 5
        ));

        Atm atm = new Atm(cash);
        Map<Currency, Long> balance = atm.getBalance();
        System.out.println("Balance: " + balance);


        atm.withdraw(Currency.KZT, 12000);
        atm.withdraw(Currency.USD, 300);

        balance = atm.getBalance();
        System.out.println("Balance: " + balance);

        atm.deposit(Map.of(
                new Banknote(Currency.KZT, 20000), 1,
                new Banknote(Currency.USD, 10), 3
        ));

        balance = atm.getBalance();
        System.out.println("Balance: " + balance);
    }
}
