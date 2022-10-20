package kr.mashup.branding.ui.application.vo;

import kr.mashup.branding.domain.application.AnswerRequestVo;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class AnswerRequest {
    /**
     * 답변 식별자
     */
    private Long answerId;
    /**
     * 질문 식별자
     */
    private Long questionId;
    /**
     * 답변 내용
     */
    private String content;

    public AnswerRequestVo toVo(){
        return AnswerRequestVo.of(answerId, questionId, content);
    }
}
