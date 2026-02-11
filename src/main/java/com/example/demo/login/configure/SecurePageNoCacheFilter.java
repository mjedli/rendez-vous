package com.example.demo.login.configure;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SecurePageNoCacheFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String uri = httpRequest.getRequestURI();

        // Appliquer uniquement aux pages sécurisées (exclure login, css, js, images, etc.)
        if (!uri.startsWith("/login") &&
                !uri.startsWith("/css") &&
                !uri.startsWith("/js") &&
                !uri.startsWith("/images") &&
                !uri.startsWith("/content") &&
                !uri.startsWith("/registrationApp") &&
                !uri.startsWith("/forgetApp") &&
                !uri.startsWith("/paybay/forget") &&
                !uri.startsWith("/paybay/update")) {

            httpResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            httpResponse.setHeader("Pragma", "no-cache");
            httpResponse.setDateHeader("Expires", 0);
        }

        chain.doFilter(request, response);
    }
}
