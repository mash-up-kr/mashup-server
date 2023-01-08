package kr.mashup.branding.domain.attendance;

import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.exception.NotFoundException;

public class AttendanceCodeNotFoundException extends NotFoundException {

    public AttendanceCodeNotFoundException(){super(ResultCode.ATTENDANCE_CODE_NOT_FOUND);}


}
