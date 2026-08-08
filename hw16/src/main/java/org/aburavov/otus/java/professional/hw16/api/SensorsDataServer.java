package org.aburavov.otus.java.professional.hw16.api;

import org.aburavov.otus.java.professional.hw16.api.model.SensorData;

public interface SensorsDataServer {
    void onReceive(SensorData sensorData);
}
