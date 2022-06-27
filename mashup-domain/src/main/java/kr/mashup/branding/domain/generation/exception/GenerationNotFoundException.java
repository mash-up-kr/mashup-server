package kr.mashup.branding.domain.generation.exception;

import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.exception.NotFoundException;

public class GenerationNotFoundException extends NotFoundException {

    public GenerationNotFoundException() {
        super(ResultCode.GENERATION_NOT_FOUND);
    }
}
