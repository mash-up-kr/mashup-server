package kr.mashup.branding.domain.application;

import java.util.List;

import lombok.Value;

/**
 * 지원서 임시저장
 */
@Value(staticConstructor = "of")
public class UpdateApplicationVo {
    String name;
    String phoneNumber;
    List<AnswerRequestVo> answerRequestVoList;
    Boolean privacyPolicyAgreed;
}
