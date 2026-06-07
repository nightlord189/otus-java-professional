package org.aburavov.otus.java.professional.hw08.dataprocessor;

import java.io.IOException;
import java.util.Map;

public interface Serializer {
    void serialize(Map<String, Double> data) throws IOException;
}
