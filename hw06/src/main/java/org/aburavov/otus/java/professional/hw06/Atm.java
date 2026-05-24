package org.aburavov.otus.java.professional.hw06;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Atm {
    private final Map<Banknote, Integer> cash;

    public Atm(Map<Banknote, Integer> cash) {
        this.cash = new HashMap<>(cash);
    }

    public Atm() {
        this.cash = new HashMap<>();
    }

    public Map<Currency, Long> getBalance() {
        Map<Currency, Long> result = new HashMap<>();

        for (Banknote b : cash.keySet()) {
            long value = result.getOrDefault(b.currency(), 0L);
            result.put(b.currency(), value + b.nominal() * cash.get(b));
        }
        return result;
    }

    public void deposit(Map<Banknote, Integer> pack) {
        for (Banknote b : pack.keySet()) {
            cash.put(b, cash.getOrDefault(b, 0) + pack.get(b));
        }
    }

    public Map<Banknote, Integer> withdraw(Currency currency, long amount) {
        Map<Banknote, Integer> plan = getWithdrawPlan(currency, amount);
        for (Banknote b : plan.keySet()) {
            cash.put(b, cash.get(b) - plan.get(b));
        }
        System.out.println("Withdraw " + amount + " " + currency + ": " + plan);
        return plan;
    }

    private Map<Banknote, Integer> getWithdrawPlan(Currency currency, long amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        List<Banknote> banknotes = cash.keySet().stream().
                filter(b -> b.currency() == currency).
                sorted(Comparator.comparingLong(Banknote::nominal).reversed()).
                toList();

        Map<Banknote, Integer> result = new HashMap<>();
        long remaining = amount;

        for (Banknote b : banknotes) {
            int available = cash.get(b);

            if (available <= 0) {
                continue;
            }

            int countToTake = (int) Math.min(remaining / b.nominal(), available);
            if (countToTake > 0) {
                result.put(b, countToTake);
                remaining -= b.nominal() * countToTake;
            }
        }

        if (remaining > 0) {
            throw new InsufficientCashException(currency, amount);
        }

        return result;
    }
}
