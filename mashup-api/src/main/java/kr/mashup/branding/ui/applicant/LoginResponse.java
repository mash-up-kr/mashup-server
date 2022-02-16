package kr.mashup.branding.ui.applicant;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class LoginResponse {
    private final String accessToken;
    @JsonProperty("applicant")
    private final ApplicantResponse applicantResponse;
}
