package com.github.fromiva.wsf.filter;

import com.github.fromiva.wsf.configuration.ApplicationInfo;
import com.github.fromiva.wsf.service.SecurityService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;

/**
 * Servlet filter to provide information about current authenticated user,
 * used by Thymeleaf template processor.
 */
@Component
@Order(1)
@RequiredArgsConstructor
public class TemplateFilter extends HttpFilter {

    /** In-memory basic application information holder. */
    private final ApplicationInfo applicationInfo;

    /** Security-specific service to support Spring Security business logic. */
    private final SecurityService securityService;

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
        String uri = request.getRequestURI();
        if (!facelessUrl(uri)) {
            String segment = uri.split("/")[1];
            request.setAttribute("page",
                    segment.substring(0, 1).toUpperCase() + segment.substring(1));
            request.setAttribute("user", securityService.getPrincipalName());
            request.setAttribute("email", securityService.getPrincipal().getEmail());
            request.setAttribute("admin", securityService.isPrincipalAnAdmin());
            request.setAttribute("rootAdmin", securityService.isPrincipalARootAdmin());
        }
        request.setAttribute("app", applicationInfo);
        chain.doFilter(request, response);
    }

    private boolean facelessUrl(final String url) {
        Set<String> faceless = Set.of("/css/", "/js/", "/images/", "/user/login");
        return "/".equals(url) || faceless.stream().anyMatch(url::startsWith);
    }
}
