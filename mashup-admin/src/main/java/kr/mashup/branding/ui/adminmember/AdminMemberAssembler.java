package kr.mashup.branding.ui.adminmember;

import org.springframework.stereotype.Component;

import kr.mashup.branding.domain.adminmember.AdminMemberSignInVo;
import kr.mashup.branding.domain.adminmember.AdminMemberVo;

@Component
public class AdminMemberAssembler {
    AdminMemberVo toAdminMemberVo(SignUpRequest signUpRequest) {
        return AdminMemberVo.of(
            signUpRequest.getUsername(),
            signUpRequest.getPassword(),
            signUpRequest.getPosition(),
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
}
