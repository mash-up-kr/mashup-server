package kr.mashup.branding.domain.mashong.Exception;

import kr.mashup.branding.domain.exception.NotFoundException;

import static kr.mashup.branding.domain.ResultCode.MASHONG_MISSION_NOT_FOUND;

public class MashongMissionNotFoundException extends NotFoundException {
    public MashongMissionNotFoundException() {
        super(MASHONG_MISSION_NOT_FOUND);
    }
}
