package kr.mashup.branding.domain.application;

import lombok.Value;

import java.time.LocalDate;
import java.util.List;

@Value(staticConstructor = "of")
public class ApplicationSubmitRequestVo {
    String applicantName;
    String phoneNumber;
    LocalDate birthdate;
    String department;
    String residence;
    List<AnswerRequestVo> answerRequestVoList;
    Boolean privacyPolicyAgreed;
}
