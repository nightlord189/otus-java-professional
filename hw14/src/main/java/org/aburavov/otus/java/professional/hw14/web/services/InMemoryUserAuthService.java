package org.aburavov.otus.java.professional.hw14.web.services;

import java.util.Map;

public class InMemoryUserAuthService implements UserAuthService {

    private final Map<String, String> data;

    public InMemoryUserAuthService(Map<String, String> loginData) {
        this.data = loginData;
    }

    @Override
    public boolean authenticate(String login, String password) {
        return password != null && password.equals(data.get(login));
    }
}
