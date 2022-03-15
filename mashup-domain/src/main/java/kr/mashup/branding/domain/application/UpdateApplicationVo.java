package kr.mashup.branding.domain.application;

import java.time.LocalDate;
import java.util.List;

import lombok.Value;

/**
 * 지원서 임시저장
 */
@Value(staticConstructor = "of")
public class UpdateApplicationVo {
    String name;
    String phoneNumber;
    LocalDate birthdate;
    String department;
    String residence;
    List<AnswerRequestVo> answerRequestVoList;
    Boolean privacyPolicyAgreed;
}
