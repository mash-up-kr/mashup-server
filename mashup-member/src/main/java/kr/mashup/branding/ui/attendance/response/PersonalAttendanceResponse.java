package kr.mashup.branding.ui.attendance.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Value;

import java.util.List;

@Getter
@Value(staticConstructor = "of")
public class PersonalAttendanceResponse {
    @ApiModelProperty(required = true)
    String memberName;
    @ApiModelProperty(required = true, notes = "출석 정보가 없을 땐 빈 리스트")
    List<AttendanceInfo> attendanceInfos;
}