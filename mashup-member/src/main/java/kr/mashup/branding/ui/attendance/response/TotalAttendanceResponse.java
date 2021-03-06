package kr.mashup.branding.ui.attendance.response;

import kr.mashup.branding.domain.member.Platform;
import lombok.Getter;
import lombok.Value;

import java.util.List;

@Getter
@Value(staticConstructor = "of")
public class TotalAttendanceResponse {
    List<PlatformInfo> platformInfos;
    Integer eventNum;
    Boolean isEnd;

    @Getter
    @Value(staticConstructor = "of")
    public static class PlatformInfo {
        Platform platform;
        Long totalCount;
        Long attendanceCount;
        Long lateCount;
    }
}
