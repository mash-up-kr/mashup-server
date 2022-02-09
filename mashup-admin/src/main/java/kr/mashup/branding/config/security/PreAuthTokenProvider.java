package kr.mashup.branding.config.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
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
            SimpleGrantedAuthority adminAuthority = new SimpleGrantedAuthority(ROLE_NAME);

            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(adminAuthority);
            return new UsernamePasswordAuthenticationToken(
                adminMember,
                "",
                authorities
            );
        }
        throw new TokenMissingException("Invalid token");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return PreAuthenticatedAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
