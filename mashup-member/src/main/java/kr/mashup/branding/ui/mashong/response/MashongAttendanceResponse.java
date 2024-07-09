package kr.mashup.branding.ui.mashong.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MashongAttendanceResponse {

    private final int attendanceCount;

    public MashongAttendanceResponse(Double missionStatus) {
        this.attendanceCount = missionStatus.intValue();
    }
}
