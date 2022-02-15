package kr.mashup.branding.ui.adminmember;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import kr.mashup.branding.domain.adminmember.AdminMember;
import kr.mashup.branding.domain.adminmember.AdminMemberLoginVo;
import kr.mashup.branding.domain.adminmember.AdminMemberVo;
import kr.mashup.branding.facade.adminmember.LoginResponseVo;
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
            signUpRequest.getPosition()
        );
    }

    AdminMemberLoginVo toAdminMemberLoginVo(LoginRequest loginRequest) {
        return AdminMemberLoginVo.of(
            loginRequest.getUsername(),
            loginRequest.getPassword()
        );
    }

    LoginResponse toLoginResponse(LoginResponseVo loginResponseVo) {
        return new LoginResponse(
            loginResponseVo.getToken(),
            toAdminMemberResponse(loginResponseVo.getAdminMember())
        );
    }

    AdminMemberResponse toAdminMemberResponse(AdminMember adminMember) {
        return new AdminMemberResponse(
            adminMember.getAdminMemberId(),
            adminMember.getUsername(),
            adminMember.getPosition()
        );
    }
}
