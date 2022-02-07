package kr.mashup.branding.config.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

public class TokenPreAuthFilter extends AbstractPreAuthenticatedProcessingFilter {
    private final String AUTHORIZATION_HEADER_NAME = "Authorization";

    @Override

    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        return resolveToken(request);
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        return resolveToken(request);
    }

    private String resolveToken(HttpServletRequest request) {
        return request.getHeader(AUTHORIZATION_HEADER_NAME);
    }
}
