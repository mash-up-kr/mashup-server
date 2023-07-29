package kr.mashup.branding.facade.adminmember;

import kr.mashup.branding.domain.adminmember.entity.AdminMember;
import kr.mashup.branding.domain.adminmember.vo.AdminMemberSignUpCommand;
import kr.mashup.branding.domain.adminmember.vo.AdminMemberVo;
import kr.mashup.branding.ui.adminmember.vo.AdminDeleteRequest;
import kr.mashup.branding.ui.adminmember.vo.AdminPasswordChangeRequest;
import kr.mashup.branding.ui.adminmember.vo.AdminPasswordResetRequest;
import org.springframework.stereotype.Service;

import kr.mashup.branding.config.jwt.JwtService;
import kr.mashup.branding.domain.adminmember.vo.AdminLoginCommand;
import kr.mashup.branding.service.adminmember.AdminMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
        final AdminPasswordResetRequest request) {

        final AdminMember executor = adminMemberService.getByAdminMemberId(adminMemberId);
        final List<AdminMember> targetAdmins = adminMemberService.getByAdminMemberIds(request.getAdminIds());

        adminMemberService.resetPassword(executor, targetAdmins, request.getResetPassword());
    }
    @Transactional
    public void changePassword(Long adminMemberId, AdminPasswordChangeRequest request) {

        final AdminMember me = adminMemberService.getByAdminMemberId(adminMemberId);

        adminMemberService.changePassword(me, request.getCurrentPassword(), request.getChangePassword());
    }

    @Transactional
    public void deleteAdminMember(
        final Long adminMemberId,
        final AdminDeleteRequest request
    ) {
        final AdminMember me = adminMemberService.getByAdminMemberId(adminMemberId);
        final List<AdminMember> targetAdmins = adminMemberService.getByAdminMemberIds(request.getAdminIds());

        targetAdmins.forEach(it -> adminMemberService.deleteAdminMember(me, it));
    }

    @Transactional(readOnly = true)
    public List<AdminMemberVo> readAdminMembers() {
        List<AdminMember> adminMembers = adminMemberService.readAdminMembers();

        return adminMembers.stream().map(AdminMemberVo::from).collect(Collectors.toList());
    }

    @Transactional
    public AdminMemberVo createAdminMember(AdminMemberSignUpCommand signUpCommand) {
        AdminMember adminMember = adminMemberService.signUp(signUpCommand);

        return AdminMemberVo.from(adminMember);
    }
}
