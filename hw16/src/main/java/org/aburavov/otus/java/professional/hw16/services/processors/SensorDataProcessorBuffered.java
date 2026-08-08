package org.aburavov.otus.java.professional.hw16.services.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.aburavov.otus.java.professional.hw16.api.SensorDataProcessor;
import org.aburavov.otus.java.professional.hw16.api.model.SensorData;
import org.aburavov.otus.java.professional.hw16.lib.SensorDataBufferedWriter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

// Этот класс нужно реализовать
@SuppressWarnings({"java:S1068", "java:S125"})
public class SensorDataProcessorBuffered implements SensorDataProcessor {
    private static final Logger log = LoggerFactory.getLogger(SensorDataProcessorBuffered.class);

    private final int bufferSize;
    private final SensorDataBufferedWriter writer;

    private final BlockingQueue<SensorData> dataBuffer;

    public SensorDataProcessorBuffered(int bufferSize, SensorDataBufferedWriter writer) {
        this.bufferSize = bufferSize;
        this.writer = writer;

        dataBuffer = new PriorityBlockingQueue<>(
                bufferSize,
                Comparator.comparing(SensorData::getMeasurementTime)
        );
    }

    @Override
    public void process(SensorData data) {
        dataBuffer.add(data);
        if (dataBuffer.size() >= bufferSize) {
            flush();
        }
    }

    public void flush() {
        List<SensorData> bufferedData = new ArrayList<>();
        dataBuffer.drainTo(bufferedData);
        if (bufferedData.isEmpty()) {
            return;
        }
        try {
            writer.writeBufferedData(bufferedData);
        } catch (Exception e) {
            log.error("Ошибка в процессе записи буфера", e);
        }
    }

    @Override
    public void onProcessingEnd() {
        flush();
    }
}
