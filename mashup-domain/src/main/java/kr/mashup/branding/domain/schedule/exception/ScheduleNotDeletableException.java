package kr.mashup.branding.domain.schedule.exception;

import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.exception.BadRequestException;

public class ScheduleNotDeletableException extends BadRequestException {
    public ScheduleNotDeletableException() {
        super(ResultCode.SCHEDULE_STARTED_TIME_OVER);
    }
}
