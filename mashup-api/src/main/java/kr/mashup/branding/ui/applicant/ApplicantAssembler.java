package kr.mashup.branding.ui.applicant;

import org.springframework.stereotype.Component;

import kr.mashup.branding.domain.applicant.Applicant;
import kr.mashup.branding.domain.applicant.LoginRequestVo;
import kr.mashup.branding.domain.applicant.LoginResponseVo;

@Component
public class ApplicantAssembler {
    public ApplicantResponse toApplicationResponse(Applicant applicant) {
        return new ApplicantResponse(
            applicant.getApplicantId(),
            applicant.getName(),
            applicant.getPhoneNumber(),
            applicant.getEmail(),
            applicant.getStatus().name()
        );
    }

    LoginRequestVo toLoginRequestVo(LoginRequest loginRequest) {
        return LoginRequestVo.of(
            loginRequest.getEmail(),
            loginRequest.getGoogleUserId()
        );
    }

    LoginResponse toLoginResponse(LoginResponseVo loginResponseVo) {
        return new LoginResponse(
            loginResponseVo.getAccessToken(),
            loginResponseVo.getApplicantId()
        );
    }
}
