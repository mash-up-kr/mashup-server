package kr.mashup.branding.config.security;

import java.util.Collections;

import kr.mashup.branding.domain.applicant.exception.ApplicantNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import kr.mashup.branding.config.jwt.JwtService;
import kr.mashup.branding.domain.applicant.Applicant;
import kr.mashup.branding.service.applicant.ApplicantService;
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
            Applicant applicant;
            try{
                applicant = applicantService.getApplicant(applicantId);
            }catch (ApplicantNotFoundException e){
                throw new ApplicantUnauthenticatedException();
            }
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
