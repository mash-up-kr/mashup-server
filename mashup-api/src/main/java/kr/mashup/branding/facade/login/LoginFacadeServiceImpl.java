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
    private final JwtService jwtService;

    @Override
    public LoginResponseVo login(LoginRequestVo loginRequestVo) {
        Applicant applicant = applicantService.join(loginRequestVo);
        return LoginResponseVo.of(
            jwtService.encode(applicant.getApplicantId()),
            applicant.getApplicantId(),
            applicant.getEmail(),
            applicant.getStatus().toString()
        );
    }
}