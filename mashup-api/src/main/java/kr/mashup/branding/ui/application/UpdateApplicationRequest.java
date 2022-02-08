package kr.mashup.branding.ui.application;

import java.util.List;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UpdateApplicationRequest {
    /**
     * 지원자 이름
     */
    private String applicantName;
    /**
     * 지원자 연락처
     */
    private String phoneNumber;
    /**
     * 각 질문에 대한 답변
     */
    private List<AnswerRequest> answers;
    /**
     * 개인정보처리방침 동의여부
     */
    private Boolean privacyPolicyAgreed;
}
