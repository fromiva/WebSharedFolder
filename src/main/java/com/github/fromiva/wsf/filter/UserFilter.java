package com.github.fromiva.wsf.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

/**
 * Servlet filter to provide information about current authenticated user,
 * used by Thymeleaf template processor.
 */
@Component
@Order(1)
public class UserFilter extends HttpFilter {

    /**
     * Adds information about current authenticated user to servlet request.
     * @param request  Servlet request
     * @param response Servlet response associated with the request
     * @param chain    Provides access to the next filter in the chain for this filter to pass
     *                 the request and response to for further processing
     * @throws IOException passed on from filter {@code chain}
     * @throws ServletException passed on from filter {@code chain}
     */
    @Override
    protected void doFilter(final HttpServletRequest request,
                            final HttpServletResponse response,
                            final FilterChain chain) throws ServletException, IOException {
        if (!facelessUrl(request.getRequestURI())) {
            request.setAttribute("user", getContext().getAuthentication().getPrincipal());
        }
        chain.doFilter(request, response);
    }

    private boolean facelessUrl(final String url) {
        Set<String> faceless = Set.of("/css/", "/js/", "/images/", "/user/login");
        return faceless.stream().anyMatch(url::startsWith);
    }
}
