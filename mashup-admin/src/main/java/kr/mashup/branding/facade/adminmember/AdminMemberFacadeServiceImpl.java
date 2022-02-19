package kr.mashup.branding.facade.adminmember;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kr.mashup.branding.config.jwt.JwtService;
import kr.mashup.branding.domain.adminmember.AdminMember;
import kr.mashup.branding.domain.adminmember.AdminMemberLoginVo;
import kr.mashup.branding.domain.adminmember.AdminMemberService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminMemberFacadeServiceImpl implements AdminMemberFacadeService {

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;
    private final AdminMemberService adminMemberService;

    @Override
    public LoginResponseVo login(AdminMemberLoginVo adminMemberLoginVo) {
        AdminMember adminMember = adminMemberService.signIn(adminMemberLoginVo);
        if (!passwordEncoder.matches(adminMemberLoginVo.getPassword(), adminMember.getPassword())) {
            throw new AdminMemberLoginFailedException();
        }
        String token = jwtService.encode(adminMember.getAdminMemberId());
        return LoginResponseVo.of(token, adminMember);
    }

    @Override
    public AdminMember getAdminMember(Long adminMemberId) {
        return adminMemberService.getByAdminMemberId(adminMemberId);
    }
}
