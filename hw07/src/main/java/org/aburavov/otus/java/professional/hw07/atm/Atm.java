package org.aburavov.otus.java.professional.hw07.atm;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class Atm implements AtmHistory, BalanceHolder {
    private AtmState currentState;
    private final List<AtmState> history = new ArrayList<>();

    public Atm(AtmState initialState) {
        save(initialState);
    }

    public AtmState getCurrentState() {
        return currentState;
    }

    @Override
    public void save(AtmState state) {
        this.currentState = state;
        history.add(new AtmState(state));
    }

    @Override
    public void resetTo(int index) {
        this.currentState = new AtmState(history.get(index));
    }

    public void deposit(Nominal nominal, int count) {
        if (count <= 0) {
            throw new IllegalArgumentException("Count must be positive: " + count);
        }

        int available = currentState.getCash().getOrDefault(nominal, 0);
        currentState.getCash().put(nominal, available+count);

        save(new AtmState(currentState.getStatus(), currentState.getCash()));
    }

    public void withdraw(Nominal nominal, int count) {
        if (count <= 0) {
            throw new IllegalArgumentException("Count must be positive: " + count);
        }
        int available = currentState.getCash().getOrDefault(nominal, 0);
        int rest = available - count;
        if (rest < 0) {
            throw new IllegalStateException("Not enough banknotes " + nominal
                    + ": requested " + count + ", available " + available);
        }
        currentState.getCash().put(nominal, rest);

        save(new AtmState(currentState.getStatus(), currentState.getCash()));
    }

    @Override
    public long getBalance() {
        return currentState.getCash().entrySet().stream()
                .mapToLong(entry -> (long) entry.getKey().getValue() * entry.getValue())
                .sum();
    }
}
