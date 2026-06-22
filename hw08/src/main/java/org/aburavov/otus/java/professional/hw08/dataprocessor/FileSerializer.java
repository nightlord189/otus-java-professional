package org.aburavov.otus.java.professional.hw08.dataprocessor;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class FileSerializer implements Serializer {
    private String fileName = "outputData.json";

    public FileSerializer(String fileName) {
        this.fileName = fileName;
    }

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void serialize(Map<String, Double> data) throws IOException {
        String resultStr = objectMapper.writeValueAsString(data);
        var outputPath = Paths.get(fileName);
        Files.writeString(outputPath, resultStr);
    }
}
