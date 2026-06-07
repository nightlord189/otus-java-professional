package org.aburavov.otus.java.professional.hw08.dataprocessor;


import org.aburavov.otus.java.professional.hw08.model.Measurement;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ProcessorAggregator implements Processor {
    private static final Comparator<String> keyComparator = Comparator.naturalOrder();

    @Override
    public Map<String, Double> process(List<Measurement> data) {
        Map<String, Double> result = new TreeMap<>(keyComparator);
        for (Measurement m : data) {
            result.merge(m.name(), m.value(), Double::sum);
        }
        return result;
    }
}
