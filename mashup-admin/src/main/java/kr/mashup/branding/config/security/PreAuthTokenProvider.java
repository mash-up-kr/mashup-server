package kr.mashup.branding.config.security;

import java.util.Collections;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import kr.mashup.branding.config.jwt.JwtService;
import kr.mashup.branding.domain.adminmember.AdminMember;
import kr.mashup.branding.domain.adminmember.AdminMemberService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PreAuthTokenProvider implements AuthenticationProvider {
    private final AdminMemberService adminMemberService;
    private final JwtService jwtService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication instanceof PreAuthenticatedAuthenticationToken) {
            String token = authentication.getPrincipal().toString();
            Long adminMemberId = jwtService.decode(token);
            AdminMember adminMember = adminMemberService.getByAdminMemberId(adminMemberId);
            return new PreAuthenticatedAuthenticationToken(
                adminMemberId,
                "",
                Collections.singletonList(new SimpleGrantedAuthority(adminMember.getPosition().name()))
            );
        }
        throw new TokenMissingException("Invalid token");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return PreAuthenticatedAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
