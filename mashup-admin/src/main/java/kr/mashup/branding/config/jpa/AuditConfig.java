package kr.mashup.branding.config.jpa;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import kr.mashup.branding.domain.adminmember.AdminMemberService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class AuditConfig {

    private final AdminMemberService adminMemberService;

    @Bean
    public AuditorAware<String> auditorAware() {
        return new SpringSecurityAuditorAware(adminMemberService);
    }
}
