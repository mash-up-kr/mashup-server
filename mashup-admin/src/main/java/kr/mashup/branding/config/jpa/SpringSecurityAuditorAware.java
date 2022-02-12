package kr.mashup.branding.config.jpa;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import kr.mashup.branding.domain.adminmember.AdminMember;

public class SpringSecurityAuditorAware implements AuditorAware<AdminMember> {

    //TODO 향후 role 테이블로 관리
    private static final String ROLE_NAME = "adminMember";

    @Override
    public Optional<AdminMember> getCurrentAuditor() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
            .map(SecurityContext::getAuthentication)
            .map(authentication -> {
                Collection<? extends GrantedAuthority> auth = authentication.getAuthorities();
                if (auth.contains(new SimpleGrantedAuthority(ROLE_NAME))) {
                    if (authentication.getPrincipal() instanceof AdminMember) {
                        return (AdminMember)authentication.getPrincipal();
                    }
                }
                return null;
            });
    }
}
