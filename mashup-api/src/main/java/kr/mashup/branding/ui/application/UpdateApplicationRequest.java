package kr.mashup.branding.ui.application;

import java.util.List;

import lombok.Data;

@Data
public class UpdateApplicationRequest {
    /**
     * 지원자 이름
     */
    private final String name;
    /**
     * 지원자 연락처
     */
    private final String phoneNumber;
    /**
     * 각 질문에 대한 답변
     */
    private final List<AnswerRequest> answers;
}
