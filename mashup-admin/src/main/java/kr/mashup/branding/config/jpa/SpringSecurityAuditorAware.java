package kr.mashup.branding.config.jpa;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;

public class SpringSecurityAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
            .map(SecurityContext::getAuthentication)
            .map(authentication -> {
                if (authentication.getPrincipal() == null) {
                    return null;
                }
                if (CollectionUtils.isEmpty(authentication.getAuthorities())) {
                    return null;
                }
                return authentication.getAuthorities()
                    .stream()
                    .findFirst()
                    .map(GrantedAuthority::getAuthority)
                    .orElse(null);
            });
    }
}
