package kr.mashup.branding.domain.application;

/**
 * 개인정보처리방침 동의하지 않고 임시저장, 제출하면 발생하는 예외
 */
public class PrivacyPolicyNotAgreedException extends RuntimeException {
    public PrivacyPolicyNotAgreedException(String message) {
        super(message);
    }
}
