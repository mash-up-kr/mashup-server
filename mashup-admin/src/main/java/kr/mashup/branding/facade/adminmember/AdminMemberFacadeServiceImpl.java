package kr.mashup.branding.facade.adminmember;

import org.springframework.stereotype.Service;

import kr.mashup.branding.config.jwt.JwtService;
import kr.mashup.branding.domain.adminmember.AdminMember;
import kr.mashup.branding.domain.adminmember.AdminMemberService;
import kr.mashup.branding.domain.adminmember.AdminMemberSignInVo;
import kr.mashup.branding.domain.adminmember.AdminMemberVo;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminMemberFacadeServiceImpl implements AdminMemberFacadeService {

    private final JwtService jwtService;
    private final AdminMemberService adminMemberService;

    @Override
    public AdminMember signUp(AdminMemberVo adminMemberVo) {
        return adminMemberService.signUp(adminMemberVo);
    }

    @Override
    public String signIn(AdminMemberSignInVo adminMemberSignInVo) {
        AdminMember adminMember = adminMemberService.signIn(adminMemberSignInVo);
        String jwt = jwtService.encode(adminMember.getAdminMemberId());
        return jwt;
    }
}
