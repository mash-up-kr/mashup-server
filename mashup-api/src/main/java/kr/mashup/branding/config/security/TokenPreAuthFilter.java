package kr.mashup.branding.config.security;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TokenPreAuthFilter extends AbstractPreAuthenticatedProcessingFilter {

    private static final Pattern BEARER_TOKEN_PATTERN = Pattern.compile("[Bb]earer (.*)");
    private static final String AUTHORIZATION_HEADER_NAME = "Authorization";

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        return resolveToken(request);
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        return resolveToken(request);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER_NAME);
        if (!StringUtils.hasText(bearerToken)) {
            log.warn("Failed to get bearerToken");
            return null;
        }
        Matcher matcher = BEARER_TOKEN_PATTERN.matcher(bearerToken);
        if (!matcher.matches()) {
            log.warn("Failed to get bearerToken");
            return null;
        } else {
            return matcher.group(1);
        }
    }
}
