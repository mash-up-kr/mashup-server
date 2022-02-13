package kr.mashup.branding.config.jpa;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import kr.mashup.branding.domain.adminmember.AdminMember;
import kr.mashup.branding.domain.adminmember.AdminMemberService;
import kr.mashup.branding.domain.adminmember.Position;

public class SpringSecurityAuditorAware implements AuditorAware<String> {

    private AdminMemberService adminMemberService;
    private List<SimpleGrantedAuthority> authoritySet;

    private SpringSecurityAuditorAware() {
    }

    public SpringSecurityAuditorAware(AdminMemberService adminMemberService) {
        this.adminMemberService = adminMemberService;
        authoritySet = Arrays.stream(Position.values())
            .map(position -> new SimpleGrantedAuthority(position.name()))
            .collect(Collectors.toList());
    }

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
            .map(SecurityContext::getAuthentication)
            .map(authentication -> {
                if (!authoritySet.retainAll(authentication.getAuthorities())) {
                    AdminMember adminMember = adminMemberService.getByAdminMemberId(
                        (Long)authentication.getPrincipal());
                    return adminMember.getPosition().name();
                }
                return null;
            });
    }
}
