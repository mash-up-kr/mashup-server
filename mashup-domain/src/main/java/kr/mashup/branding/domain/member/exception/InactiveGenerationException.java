package kr.mashup.branding.domain.member.exception;

import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.exception.NotFoundException;

public class InactiveGenerationException extends NotFoundException {
	public InactiveGenerationException() {
		super(ResultCode.INACTIVE_GENERATION);
	}
}
