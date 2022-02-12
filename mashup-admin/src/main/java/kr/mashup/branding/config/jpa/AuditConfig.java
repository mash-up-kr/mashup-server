package kr.mashup.branding.config.jpa;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import kr.mashup.branding.domain.adminmember.AdminMember;

@Configuration
public class AuditConfig {

    @Bean
    public AuditorAware<AdminMember> auditorAware() {
        return new SpringSecurityAuditorAware();
    }
}
