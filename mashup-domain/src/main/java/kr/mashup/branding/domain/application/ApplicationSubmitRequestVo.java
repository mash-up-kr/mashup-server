package kr.mashup.branding.domain.application;

import java.util.List;

import lombok.Value;

@Value(staticConstructor = "of")
public class ApplicationSubmitRequestVo {
    String applicantName;
    String phoneNumber;
    List<AnswerRequestVo> answerRequestVoList;
    Boolean privacyPolicyAgreed;
}
