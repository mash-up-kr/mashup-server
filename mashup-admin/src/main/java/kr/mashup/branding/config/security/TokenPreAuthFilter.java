package kr.mashup.branding.config.security;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.util.StringUtils;

public class TokenPreAuthFilter extends AbstractPreAuthenticatedProcessingFilter {

    private final String AUTHORIZATION_HEADER_NAME = "Authorization";
    private final Pattern BEARER_TOKEN_PATTERN = Pattern.compile("[Bb]earer (.*)");

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        return resolveToken(request);
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        return resolveToken(request);
    }

    private String resolveToken(HttpServletRequest request) {
        String token = request.getHeader(AUTHORIZATION_HEADER_NAME);
        if (!StringUtils.hasText(token)) {
            return null;
        }
        Matcher matcher = BEARER_TOKEN_PATTERN.matcher(token);
        if (!matcher.matches()) {
            return null;
        } else {
            return matcher.group(1);
        }
    }
}
