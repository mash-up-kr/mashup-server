package kr.mashup.branding.config.security;

import java.util.Collections;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import kr.mashup.branding.config.jwt.JwtService;
import kr.mashup.branding.domain.applicant.Applicant;
import kr.mashup.branding.domain.applicant.ApplicantService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PreAuthTokenProvider implements AuthenticationProvider {
    private final ApplicantService applicantService;
    private final JwtService jwtService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication instanceof PreAuthenticatedAuthenticationToken) {
            String token = authentication.getPrincipal().toString();
            Long applicantId = jwtService.decode(token);
            // TODO: 지원자 조회 실패하는 경우, AuthenticationException 으로 예외번역
            Applicant applicant = applicantService.getApplicant(applicantId);
            return new PreAuthenticatedAuthenticationToken(
                applicant.getApplicantId(),
                "",
                Collections.singletonList(new SimpleGrantedAuthority(SecurityConfig.APPLICANT_ROLE_NAME))
            );
        }
        throw new TokenMissingException("Invalid token");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return PreAuthenticatedAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
