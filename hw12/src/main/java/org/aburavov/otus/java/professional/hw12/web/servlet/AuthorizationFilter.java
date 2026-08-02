package org.aburavov.otus.java.professional.hw12.web.servlet;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class AuthorizationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        var httpRequest = (HttpServletRequest) request;
        var httpResponse = (HttpServletResponse) response;

        var session = httpRequest.getSession(false);
        if (session != null && session.getAttribute(LoginServlet.SESSION_USER_ATTR) != null) {
            chain.doFilter(request, response);
        } else {
            httpResponse.sendRedirect("/login");
        }
    }
}
