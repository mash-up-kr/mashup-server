package kr.mashup.branding.domain.application;

import lombok.Value;

import java.util.List;

/**
 * 지원서 임시저장
 */
@Value(staticConstructor = "of")
public class UpdateApplicationVo {
    List<AnswerRequestVo> answerRequestVoList;
}
