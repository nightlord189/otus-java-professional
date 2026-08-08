package org.aburavov.otus.java.professional.hw16.api;

import java.util.concurrent.TimeUnit;
import org.aburavov.otus.java.professional.hw16.api.model.SensorData;

public interface SensorsDataChannel {
    boolean push(SensorData sensorData);

    boolean isEmpty();

    SensorData take(long timeout, TimeUnit unit) throws InterruptedException;
}
