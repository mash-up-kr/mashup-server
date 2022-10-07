package kr.mashup.branding.facade.adminmember;

import kr.mashup.branding.domain.adminmember.vo.AdminMemberVo;
import org.springframework.stereotype.Service;

import kr.mashup.branding.config.jwt.JwtService;
import kr.mashup.branding.domain.adminmember.entity.AdminMember;
import kr.mashup.branding.domain.adminmember.vo.AdminMemberLoginCommand;
import kr.mashup.branding.service.adminmember.AdminMemberService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminMemberFacadeService {

    private final JwtService jwtService;
    private final AdminMemberService adminMemberService;

    public LoginResponseVo login(AdminMemberLoginCommand adminMemberLoginCommand) {

        final AdminMemberVo adminMemberVo = adminMemberService.logIn(adminMemberLoginCommand);
        final String token = jwtService.encode(adminMemberVo.getAdminMemberId());

        return LoginResponseVo.of(token, adminMemberVo);
    }

    public AdminMemberVo getAdminMember(Long adminMemberId) {
        return adminMemberService.getByAdminMemberId(adminMemberId);
    }
}
