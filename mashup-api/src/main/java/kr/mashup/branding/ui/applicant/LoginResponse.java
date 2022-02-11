package kr.mashup.branding.ui.applicant;

import lombok.Data;

@Data
public class LoginResponse {
    private final String accessToken;
    private final Long applicantId;
}
