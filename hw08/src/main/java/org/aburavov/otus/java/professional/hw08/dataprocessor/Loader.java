package org.aburavov.otus.java.professional.hw08.dataprocessor;

import org.aburavov.otus.java.professional.hw08.model.Measurement;

import java.io.IOException;
import java.util.List;

public interface Loader {
    List<Measurement> load() throws IOException;
}
