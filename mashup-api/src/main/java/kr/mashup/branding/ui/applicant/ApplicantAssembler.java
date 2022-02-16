package kr.mashup.branding.ui.applicant;

import org.springframework.stereotype.Component;

import kr.mashup.branding.domain.applicant.Applicant;
import kr.mashup.branding.domain.applicant.GoogleLoginRequestVo;
import kr.mashup.branding.domain.applicant.LoginResponseVo;

@Component
public class ApplicantAssembler {
    public ApplicantResponse toApplicantResponse(Applicant applicant) {
        return new ApplicantResponse(
            applicant.getApplicantId(),
            applicant.getName(),
            applicant.getPhoneNumber(),
            applicant.getEmail(),
            applicant.getStatus()
        );
    }

    GoogleLoginRequestVo toGoogleLoginRequestVo(GoogleLoginRequest googleLoginRequest) {
        return GoogleLoginRequestVo.of(
            googleLoginRequest.getGoogleIdToken()
        );
    }

    LoginResponse toLoginResponse(LoginResponseVo loginResponseVo) {
        return new LoginResponse(
            loginResponseVo.getAccessToken(),
            loginResponseVo.getApplicant()
        );
    }
}
