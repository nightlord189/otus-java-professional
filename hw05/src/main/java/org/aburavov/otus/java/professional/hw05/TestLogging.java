package org.aburavov.otus.java.professional.hw05;

public class TestLogging implements TestLoggingInterface {
    @Log
    @Override
    public void calculation(int param1) {
    }

    @Log
    @Override
    public void calculation(int param1, int param2) {
    }

    @Log
    @Override
    public void calculation(int param1, int param2, String param3) {
    }

    @Override
    public void noLogging(int param1) {
    }
}
