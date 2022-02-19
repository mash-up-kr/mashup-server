package kr.mashup.branding.domain.team;

import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.exception.NotFoundException;

public class TeamNotFoundException extends NotFoundException {
    public TeamNotFoundException() {
        super(ResultCode.TEAM_NOT_FOUND);
    }
}
