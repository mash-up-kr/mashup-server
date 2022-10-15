package kr.mashup.branding.ui.applicant.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class LoginResponse {
    private final String accessToken;
    @JsonProperty("applicant")
    private final ApplicantResponse applicantResponse;

    public static LoginResponse of(String accessToken, ApplicantResponse applicantResponse){
        return new LoginResponse(accessToken, applicantResponse);
    }
}
