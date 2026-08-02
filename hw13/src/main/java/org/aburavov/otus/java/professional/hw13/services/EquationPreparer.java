package org.aburavov.otus.java.professional.hw13.services;

import java.util.List;
import org.aburavov.otus.java.professional.hw13.model.Equation;

public interface EquationPreparer {
    List<Equation> prepareEquationsFor(int base);
}
