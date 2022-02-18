package kr.mashup.branding.domain.applicant;

import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.exception.NotFoundException;

public class ApplicantNotFoundException extends NotFoundException {
    public ApplicantNotFoundException() {
        super(ResultCode.APPLICANT_NOT_FOUND);
    }
}
