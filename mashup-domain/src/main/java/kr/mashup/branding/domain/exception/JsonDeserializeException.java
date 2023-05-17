package kr.mashup.branding.domain.exception;

import kr.mashup.branding.domain.ResultCode;

public class JsonDeserializeException extends MashupServerException {
    public JsonDeserializeException() {
        super(ResultCode.JSON_DESERIALIZE_UNABLE);
    }
}
