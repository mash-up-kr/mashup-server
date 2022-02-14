package kr.mashup.branding.facade.adminmember;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kr.mashup.branding.config.jwt.JwtService;
import kr.mashup.branding.config.security.PasswordInvalidException;
import kr.mashup.branding.domain.adminmember.AdminMember;
import kr.mashup.branding.domain.adminmember.AdminMemberService;
import kr.mashup.branding.domain.adminmember.AdminMemberSignInVo;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminMemberFacadeServiceImpl implements AdminMemberFacadeService {

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;
    private final AdminMemberService adminMemberService;

    @Override
    public SignInVo signIn(AdminMemberSignInVo adminMemberSignInVo) {
        AdminMember adminMember = adminMemberService.signIn(adminMemberSignInVo);
        if (!passwordEncoder.matches(adminMemberSignInVo.getPassword(), adminMember.getPassword())) {
            throw new PasswordInvalidException();
        }
        String token = jwtService.encode(adminMember.getAdminMemberId());
        return SignInVo.of(token, adminMember);
    }
}
