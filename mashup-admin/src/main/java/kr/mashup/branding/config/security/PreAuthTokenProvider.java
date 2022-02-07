package kr.mashup.branding.config.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import kr.mashup.branding.config.jwt.JwtService;
import kr.mashup.branding.domain.adminmember.AdminMember;
import kr.mashup.branding.domain.adminmember.AdminMemberService;

public class PreAuthTokenProvider implements AuthenticationProvider {

    private final String ROLE_NAME = "adminMember";

    @Autowired
    private AdminMemberService adminMemberService;
    @Autowired
    private JwtService jwtService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication instanceof PreAuthenticatedAuthenticationToken) {
            String token = authentication.getPrincipal().toString();
            Long adminMemberId = jwtService.decode(token);
            AdminMember adminMember = adminMemberService.getByAdminMemberId(adminMemberId);
            return new UsernamePasswordAuthenticationToken(
                adminMember,
                "",
                List.of(new SimpleGrantedAuthority(ROLE_NAME)));
        }
        throw new TokenMissingException();
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return PreAuthenticatedAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
