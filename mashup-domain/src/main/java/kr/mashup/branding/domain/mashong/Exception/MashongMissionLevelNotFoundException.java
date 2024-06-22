package kr.mashup.branding.domain.mashong.Exception;

import kr.mashup.branding.domain.exception.NotFoundException;

import static kr.mashup.branding.domain.ResultCode.MASHONG_MISSION_LEVEL_NOT_FOUND;

public class MashongMissionLevelNotFoundException extends NotFoundException {
    public MashongMissionLevelNotFoundException() {
        super(MASHONG_MISSION_LEVEL_NOT_FOUND);
    }
}
