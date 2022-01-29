package kr.mashup.branding.domain.application;

import lombok.Value;

import java.util.List;

/**
 * 지원서 임시저장
 */
@Value
public class UpdateApplicationVo {
    Long applicationId;
    List<AnswerRequestVo> answerRequestVoList;
}
