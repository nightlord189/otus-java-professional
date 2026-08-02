package org.aburavov.otus.java.professional.hw14.web.services;

import java.io.IOException;
import java.util.Map;

public interface TemplateProcessor {

    String getPage(String filename, Map<String, Object> data) throws IOException;
}
