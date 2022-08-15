package kr.mashup.branding.ui.attendance.response;

import io.swagger.annotations.ApiModelProperty;
import kr.mashup.branding.domain.member.Platform;
import lombok.Getter;
import lombok.Value;

import java.util.List;

@Getter
@Value(staticConstructor = "of")
public class PlatformAttendanceResponse {
    @ApiModelProperty(required = true)
    Platform platform;
    @ApiModelProperty(required = true)
    List<MemberInfo> members;

    @Getter
    @Value(staticConstructor = "of")
    public static class MemberInfo {
        @ApiModelProperty(required = true)
        String name;
        @ApiModelProperty(required = true, notes = "출석 정보가 없을 땐 빈 리스트")
        List<AttendanceInfo> attendanceInfos;
    }
}
