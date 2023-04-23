package kr.mashup.branding.domain.storage;

import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.exception.NotFoundException;

public class StorageNotFoundException extends NotFoundException {

    public StorageNotFoundException() {
        super(ResultCode.STORAGE_NOT_FOUND);
    }
}
