package org.aburavov.otus.java.professional.hw14.web.servlet;

import jakarta.servlet.*;
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
