package kr.mashup.branding.facade.login;

import kr.mashup.branding.domain.applicant.LoginRequestVo;
import kr.mashup.branding.domain.applicant.LoginResponseVo;

public interface LoginFacadeService {
    LoginResponseVo login(LoginRequestVo loginRequestVo);
}
