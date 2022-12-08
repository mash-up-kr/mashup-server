package kr.mashup.branding.domain.applicant.vo;

import kr.mashup.branding.domain.applicant.Applicant;
import lombok.Value;

@Value(staticConstructor = "of")
public class LoginResponseVo {
    String accessToken;
    Applicant applicant;
}
