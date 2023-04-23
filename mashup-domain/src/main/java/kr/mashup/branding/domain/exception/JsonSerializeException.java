package kr.mashup.branding.domain.exception;

import kr.mashup.branding.domain.ResultCode;

public class JsonSerializeException extends MashupServerException {
    public JsonSerializeException() {
        super(ResultCode.JSON_SERIALIZE_UNABLE);
    }
}
