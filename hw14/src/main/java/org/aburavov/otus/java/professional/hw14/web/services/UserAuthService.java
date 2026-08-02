package org.aburavov.otus.java.professional.hw14.web.services;

public interface UserAuthService {

    boolean authenticate(String login, String password);
}
