package kr.mashup.branding.ui.application.vo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import kr.mashup.branding.domain.application.ApplicationSubmitRequestVo;
import lombok.Getter;
import lombok.ToString;
import org.springframework.util.Assert;

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
        Assert.hasText(applicantName, "'applicantName' must not be blank");
        Assert.hasText(phoneNumber, "'phoneNumber' must not be blank");
        Assert.notNull(birthdate, "'birthdate' must not be null");
        Assert.hasText(department, "'residence' must not be blank");
        Assert.hasText(residence, "'department' must not be blank");
        return ApplicationSubmitRequestVo.of(
            applicantName,
            phoneNumber,
            birthdate,
            department,
            residence,
            Optional.ofNullable(answers)
                .map(it -> it.stream()
                    .map(AnswerRequest::toVo)
                    .collect(Collectors.toList()))
                .orElse(null),
            privacyPolicyAgreed);
    }
}
