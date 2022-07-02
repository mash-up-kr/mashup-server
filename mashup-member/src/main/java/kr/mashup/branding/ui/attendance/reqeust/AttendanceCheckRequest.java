package kr.mashup.branding.ui.attendance.reqeust;

import lombok.Getter;

@Getter
public class AttendanceCheckRequest {
    private Long memberId;
    private Long eventId;
}
