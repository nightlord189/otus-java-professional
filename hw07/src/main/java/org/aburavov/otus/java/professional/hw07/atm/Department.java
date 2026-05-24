package org.aburavov.otus.java.professional.hw07.atm;

import java.util.ArrayList;
import java.util.List;

public class Department implements BalanceHolder {
    private final List<Atm> atms = new ArrayList<>();
    private final Command restoreCommand = new RestoreAllCommand(atms);

    public void addAtm(Atm atm) {
        atms.add(atm);
    }

    @Override
    public long getBalance() {
        return atms.stream()
                .mapToLong(Atm::getBalance)
                .sum();
    }

    public void restoreAll() {
        restoreCommand.execute();
    }
}
