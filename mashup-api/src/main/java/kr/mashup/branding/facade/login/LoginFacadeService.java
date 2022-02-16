package kr.mashup.branding.facade.login;

import kr.mashup.branding.domain.applicant.GoogleLoginRequestVo;
import kr.mashup.branding.domain.applicant.LoginResponseVo;

public interface LoginFacadeService {
    LoginResponseVo login(GoogleLoginRequestVo googleLoginRequestVo);
}
