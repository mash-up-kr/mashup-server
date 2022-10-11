package kr.mashup.branding.ui.application.vo;

import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ApplicationSubmitRequest {
    private String applicantName;
    private String phoneNumber;
    private LocalDate birthdate;
    private String department;
    private String residence;
    private List<AnswerRequest> answers;
    private Boolean privacyPolicyAgreed;
}
