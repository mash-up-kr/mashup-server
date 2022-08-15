package kr.mashup.branding.ui.attendance.response;

import io.swagger.annotations.ApiModelProperty;
import kr.mashup.branding.domain.member.Platform;
import lombok.Getter;
import lombok.Value;

import java.util.List;

@Getter
@Value(staticConstructor = "of")
public class TotalAttendanceResponse {
    @ApiModelProperty(required = true)
    List<PlatformInfo> platformInfos;
    @ApiModelProperty(required = true, notes = "몇 부인지 나타내는 값 (1 -> 1부, 2 -> 2부)")
    Integer eventNum;
    @ApiModelProperty(required = true)
    Boolean isEnd;

    @Getter
    @Value(staticConstructor = "of")
    public static class PlatformInfo {
        @ApiModelProperty(required = true)
        Platform platform;
        @ApiModelProperty(required = true, notes = "전체 인원수")
        Long totalCount;
        @ApiModelProperty(required = true, notes = "출석 인원수")
        Long attendanceCount;
        @ApiModelProperty(required = true, notes = "지각 인원수")
        Long lateCount;
    }
}
