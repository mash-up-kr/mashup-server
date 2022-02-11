package kr.mashup.branding.ui.applicant;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class LoginRequest {
    private String email;
    private String googleUserId;
}
