package kr.mashup.branding.domain.schedule.exception;

import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.exception.BadRequestException;

public class ScheduleNotUpdatableException extends BadRequestException {

    public ScheduleNotUpdatableException() {
        super(ResultCode.SCHEDULE_NOT_UPDATABLE);
    }
}
