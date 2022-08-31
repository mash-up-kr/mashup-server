package kr.mashup.branding.domain.exception;

import kr.mashup.branding.domain.ResultCode;

public class GenerationIntegrityFailException extends MashupServerException{

    public GenerationIntegrityFailException(){
        super(ResultCode.MEMBER_GENERATION_NOT_FOUND);
    }

}
