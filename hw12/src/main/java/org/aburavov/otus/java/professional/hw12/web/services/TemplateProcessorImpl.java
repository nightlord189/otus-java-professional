package org.aburavov.otus.java.professional.hw12.web.services;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class TemplateProcessorImpl implements TemplateProcessor {

    private final Configuration configuration;

    public TemplateProcessorImpl(String templatesDir) {
        configuration = new Configuration(Configuration.VERSION_2_3_34);
        configuration.setTemplateLoader(new ClassTemplateLoader(getClass().getClassLoader(), templatesDir));
        configuration.setDefaultEncoding(StandardCharsets.UTF_8.name());
    }

    @Override
    public String getPage(String filename, Map<String, Object> data) throws IOException {
        try (var writer = new StringWriter()) {
            var template = configuration.getTemplate(filename);
            template.process(data, writer);
            return writer.toString();
        } catch (TemplateException e) {
            throw new IOException(e);
        }
    }
}
