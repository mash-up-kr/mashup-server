package kr.mashup.branding.domain.application;

import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.exception.BadRequestException;

/**
 * 개인정보처리방침 동의하지 않고 임시저장, 제출하면 발생하는 예외
 */
public class PrivacyPolicyNotAgreedException extends BadRequestException {
    public PrivacyPolicyNotAgreedException(String message) {
        super(ResultCode.APPLICATION_PRIVACY_POLICY_NOT_AGREED, message);
    }
}
