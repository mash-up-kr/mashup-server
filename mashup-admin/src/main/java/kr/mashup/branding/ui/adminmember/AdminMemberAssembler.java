package kr.mashup.branding.ui.adminmember;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import kr.mashup.branding.domain.adminmember.AdminMember;
import kr.mashup.branding.domain.adminmember.AdminMemberSignInVo;
import kr.mashup.branding.domain.adminmember.AdminMemberVo;
import kr.mashup.branding.facade.adminmember.SignInVo;
import kr.mashup.branding.ui.team.TeamResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class AdminMemberAssembler {

    private final PasswordEncoder passwordEncoder;

    AdminMemberVo toAdminMemberVo(SignUpRequest signUpRequest) {
        String encodedPassword = passwordEncoder.encode(signUpRequest.getPassword());
        return AdminMemberVo.of(
            signUpRequest.getUsername(),
            encodedPassword,
            signUpRequest.getPhoneNumber(),
            signUpRequest.getTeamId(),
            signUpRequest.getDescription()
        );
    }

    AdminMemberSignInVo toAdminMemberSignInVo(SignInRequest signInRequest) {
        return AdminMemberSignInVo.of(
            signInRequest.getUsername(),
            signInRequest.getPassword()
        );
    }

    SignInResponse toSignInResponse(SignInVo signInVo) {
        return new SignInResponse(signInVo.getToken(), toAdminMemberResponse(signInVo.getAdminMember()));
    }

    SignUpResponse toSignUpResponse(AdminMember adminMember) {
        return new SignUpResponse(adminMember.getAdminMemberId());
    }

    AdminMemberResponse toAdminMemberResponse(AdminMember adminMember) {
        return new AdminMemberResponse(
            adminMember.getAdminMemberId(),
            adminMember.getUsername(),
            new TeamResponse(adminMember.getTeam().getTeamId(), adminMember.getTeam().getName()),
            adminMember.getDescription()
        );
    }
}
