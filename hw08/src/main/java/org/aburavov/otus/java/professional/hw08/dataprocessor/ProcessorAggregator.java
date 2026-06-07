package org.aburavov.otus.java.professional.hw08.dataprocessor;


import org.aburavov.otus.java.professional.hw08.model.Measurement;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ProcessorAggregator implements Processor {
    @Override
    public Map<String, Double> process(List<Measurement> data) {
        // группирует выходящий список по name, при этом суммирует поля value
        return Collections.emptyMap();
    }
}
