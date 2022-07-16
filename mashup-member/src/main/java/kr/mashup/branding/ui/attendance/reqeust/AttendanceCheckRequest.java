package kr.mashup.branding.ui.attendance.reqeust;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class AttendanceCheckRequest {
    private Long memberId;
    private Long eventId;
}
