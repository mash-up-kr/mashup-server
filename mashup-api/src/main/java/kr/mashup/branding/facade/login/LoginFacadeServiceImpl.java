package kr.mashup.branding.facade.login;

import org.springframework.stereotype.Service;

import kr.mashup.branding.config.jwt.JwtService;
import kr.mashup.branding.domain.applicant.Applicant;
import kr.mashup.branding.domain.applicant.ApplicantService;
import kr.mashup.branding.domain.applicant.LoginRequestVo;
import kr.mashup.branding.domain.applicant.LoginResponseVo;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginFacadeServiceImpl implements LoginFacadeService {

    private final ApplicantService applicantService;
    private final JwtService jwtService; // 수정

    @Override
    public LoginResponseVo login(LoginRequestVo loginRequestVo) {
        Applicant applicant = applicantService.join(loginRequestVo);
        LoginResponseVo loginResponseVo = LoginResponseVo.of(
            jwtService.encode(applicant.getApplicantId()),
            applicant.getApplicantId()
        );
        return loginResponseVo;
    }
}