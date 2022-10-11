package kr.mashup.branding.ui.application.vo;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import kr.mashup.branding.domain.application.ApplicationSubmitRequestVo;
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

    public ApplicationSubmitRequestVo toVo(){
        return ApplicationSubmitRequestVo.of(
            applicantName,
            phoneNumber,
            birthdate,
            department,
            residence,
            answers.stream()
                .map(AnswerRequest::toVo)
                .collect(Collectors.toList()),
            privacyPolicyAgreed);
    }
}
