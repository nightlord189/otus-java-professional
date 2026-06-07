package org.aburavov.otus.java.professional.hw08.dataprocessor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aburavov.otus.java.professional.hw08.model.Measurement;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ResourcesFileLoader implements Loader {
    private final String fileName;

    public ResourcesFileLoader(String fileName) {
        this.fileName = fileName;
    }

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<Measurement> load() throws IOException {
        String fullPath = Thread.currentThread().getContextClassLoader().getResource(fileName).getFile();
        byte[] bytes = Files.readAllBytes(Paths.get(fullPath));
        var result = objectMapper.readValue(bytes, new TypeReference<List<Measurement>>() {});
        return result;
    }
}
