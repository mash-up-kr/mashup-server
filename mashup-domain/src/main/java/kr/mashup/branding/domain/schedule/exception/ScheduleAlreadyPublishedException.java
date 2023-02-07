package kr.mashup.branding.domain.schedule.exception;

import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.exception.BadRequestException;

public class ScheduleAlreadyPublishedException extends BadRequestException {
    public ScheduleAlreadyPublishedException() {
        super(ResultCode.SCHEDULE_ALREADY_PUBLISHED);
    }
}
