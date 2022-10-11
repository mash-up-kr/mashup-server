package kr.mashup.branding.ui.applicant;

import kr.mashup.branding.ui.applicant.vo.ApplicantResponse;
import kr.mashup.branding.ui.applicant.vo.LoginRequest;
import kr.mashup.branding.ui.applicant.vo.LoginResponse;
import org.springframework.stereotype.Component;

import kr.mashup.branding.domain.applicant.Applicant;
import kr.mashup.branding.domain.applicant.LoginRequestVo;
import kr.mashup.branding.domain.applicant.LoginResponseVo;

@Component
public class ApplicantAssembler {
    public ApplicantResponse toApplicantResponse(Applicant applicant) {
        return new ApplicantResponse(
            applicant.getApplicantId(),
            applicant.getEmail(),
            applicant.getName(),
            applicant.getPhoneNumber(),
            applicant.getBirthdate(),
            applicant.getDepartment(),
            applicant.getResidence(),
            applicant.getStatus()
        );
    }

    LoginRequestVo toLoginRequestVo(LoginRequest loginRequest) {
        return LoginRequestVo.of(
            loginRequest.getGoogleIdToken()
        );
    }

    LoginResponse toLoginResponse(LoginResponseVo loginResponseVo) {
        return new LoginResponse(
            loginResponseVo.getAccessToken(),
            toApplicantResponse(loginResponseVo.getApplicant())
        );
    }
}
