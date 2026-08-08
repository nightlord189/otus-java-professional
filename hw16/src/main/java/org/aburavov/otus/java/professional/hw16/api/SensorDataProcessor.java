package org.aburavov.otus.java.professional.hw16.api;

import org.aburavov.otus.java.professional.hw16.api.model.SensorData;

public interface SensorDataProcessor {
    void process(SensorData data);

    default void onProcessingEnd() {}
}
