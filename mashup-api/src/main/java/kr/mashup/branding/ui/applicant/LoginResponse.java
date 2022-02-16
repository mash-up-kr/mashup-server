package kr.mashup.branding.ui.applicant;

import kr.mashup.branding.domain.applicant.Applicant;
import lombok.Data;

@Data
public class LoginResponse {
    private final String accessToken;
    private final Applicant applicant;
}
