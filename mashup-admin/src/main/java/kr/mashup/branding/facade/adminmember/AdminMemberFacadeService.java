package kr.mashup.branding.facade.adminmember;

import kr.mashup.branding.domain.adminmember.entity.AdminMember;
import kr.mashup.branding.domain.adminmember.vo.AdminMemberVo;
import org.springframework.stereotype.Service;

import kr.mashup.branding.config.jwt.JwtService;
import kr.mashup.branding.domain.adminmember.vo.AdminLoginCommand;
import kr.mashup.branding.service.adminmember.AdminMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminMemberFacadeService {

    private final JwtService jwtService;
    private final AdminMemberService adminMemberService;

    public LoginResponseVo login(AdminLoginCommand adminLoginCommand) {

        final AdminMember adminMember = adminMemberService.logIn(adminLoginCommand);
        final AdminMemberVo adminMemberVo = AdminMemberVo.from(adminMember);
        final String token = jwtService.encode(adminMemberVo.getAdminMemberId());

        return LoginResponseVo.of(token, adminMemberVo);
    }

    public AdminMemberVo getAdminMember(Long adminMemberId) {

        final AdminMember adminMember = adminMemberService.getByAdminMemberId(adminMemberId);
        return AdminMemberVo.from(adminMember);
    }
}
