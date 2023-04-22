package kr.mashup.branding.facade.adminmember;

import kr.mashup.branding.domain.adminmember.entity.AdminMember;
import kr.mashup.branding.domain.adminmember.vo.AdminMemberVo;
import kr.mashup.branding.domain.exception.ForbiddenException;
import kr.mashup.branding.service.adminmember.LeaderCheckService;
import kr.mashup.branding.ui.adminmember.AdminPasswordChangeRequest;
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

    @Transactional
    public void resetPassword(
        final Long adminMemberId,
        final Long targetAdminId,
        final String resetPassword) {

        final AdminMember executor = adminMemberService.getByAdminMemberId(adminMemberId);
        final AdminMember targetAdmin = adminMemberService.getByAdminMemberId(targetAdminId);

        adminMemberService.resetPassword(targetAdmin, executor, resetPassword);
    }
    @Transactional
    public void changePassword(Long adminMemberId, AdminPasswordChangeRequest request) {

        final AdminMember me = adminMemberService.getByAdminMemberId(adminMemberId);

        adminMemberService.changePassword(me, request.getCurrentPassword(), request.getChangePassword());
    }

    @Transactional
    public void deleteAdminMember(final Long adminMemberId,final Long targetAdminId) {
        final AdminMember me = adminMemberService.getByAdminMemberId(adminMemberId);
        final AdminMember targetAdmin = adminMemberService.getByAdminMemberId(targetAdminId);
        adminMemberService.deleteAdminMember(me, targetAdmin);
    }
}
