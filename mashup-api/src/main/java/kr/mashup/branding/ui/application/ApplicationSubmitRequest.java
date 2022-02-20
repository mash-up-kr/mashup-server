package kr.mashup.branding.ui.application;

import java.util.List;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ApplicationSubmitRequest {
    private String applicantName;
    private String phoneNumber;
    private List<AnswerRequest> answers;
    private Boolean privacyPolicyAgreed;
}
