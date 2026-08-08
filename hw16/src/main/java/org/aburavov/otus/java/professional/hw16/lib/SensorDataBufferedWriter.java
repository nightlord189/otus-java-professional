package org.aburavov.otus.java.professional.hw16.lib;

import java.util.List;
import org.aburavov.otus.java.professional.hw16.api.model.SensorData;

public interface SensorDataBufferedWriter {
    void writeBufferedData(List<SensorData> bufferedData);
}
