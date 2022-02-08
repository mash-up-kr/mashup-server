package kr.mashup.branding.ui.adminmember;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import kr.mashup.branding.domain.adminmember.AdminMemberSignInVo;
import kr.mashup.branding.domain.adminmember.AdminMemberVo;
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
