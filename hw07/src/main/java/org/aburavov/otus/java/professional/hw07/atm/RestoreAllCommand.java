package org.aburavov.otus.java.professional.hw07.atm;

import java.util.List;

public class RestoreAllCommand implements Command {
    private final List<Atm> atms;

    public RestoreAllCommand(List<Atm> atms) {
        this.atms = atms;
    }

    @Override
    public void execute() {
        atms.forEach(atm -> atm.resetTo(0));
    }
}
