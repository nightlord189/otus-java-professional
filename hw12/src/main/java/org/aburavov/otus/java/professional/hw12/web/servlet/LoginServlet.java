package org.aburavov.otus.java.professional.hw12.web.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aburavov.otus.java.professional.hw12.web.services.TemplateProcessor;
import org.aburavov.otus.java.professional.hw12.web.services.UserAuthService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginServlet extends HttpServlet {

    public static final String SESSION_USER_ATTR = "login";

    private static final String LOGIN_PAGE_TEMPLATE = "login.html";
    private static final int MAX_INACTIVE_INTERVAL_SEC = 30 * 60;

    private final transient TemplateProcessor templateProcessor;
    private final transient UserAuthService userAuthService;

    public LoginServlet(TemplateProcessor templateProcessor, UserAuthService userAuthService) {
        this.templateProcessor = templateProcessor;
        this.userAuthService = userAuthService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        renderLoginPage(response, Map.of());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var login = request.getParameter("login");
        var password = request.getParameter("password");

        if (userAuthService.authenticate(login, password)) {
            var session = request.getSession();
            session.setMaxInactiveInterval(MAX_INACTIVE_INTERVAL_SEC);
            session.setAttribute(SESSION_USER_ATTR, login);
            response.sendRedirect("/clients");
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            renderLoginPage(response, Map.of("error", "Неверный логин или пароль"));
        }
    }

    private void renderLoginPage(HttpServletResponse response, Map<String, Object> data) throws IOException {
        response.setContentType("text/html; charset=utf-8");
        response.getWriter().println(templateProcessor.getPage(LOGIN_PAGE_TEMPLATE, new HashMap<>(data)));
    }
}
