package org.aburavov.otus.java.professional.hw08.dataprocessor;

import org.aburavov.otus.java.professional.hw08.model.Measurement;

import java.util.List;
import java.util.Map;

public interface Processor {
    Map<String, Double> process(List<Measurement> data);
}
