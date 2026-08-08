package org.aburavov.otus.java.professional.hw16.services;

import org.aburavov.otus.java.professional.hw16.api.SensorsDataChannel;
import org.aburavov.otus.java.professional.hw16.api.SensorsDataServer;
import org.aburavov.otus.java.professional.hw16.api.model.SensorData;

public class SensorsDataServerImpl implements SensorsDataServer {

    private final SensorsDataChannel sensorsDataChannel;

    public SensorsDataServerImpl(SensorsDataChannel sensorsDataChannel) {
        this.sensorsDataChannel = sensorsDataChannel;
    }

    @Override
    public void onReceive(SensorData sensorData) {
        sensorsDataChannel.push(sensorData);
    }
}
