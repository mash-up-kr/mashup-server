package kr.mashup.branding.ui.attendance;

import io.swagger.annotations.ApiOperation;
import kr.mashup.branding.domain.member.Platform;
import kr.mashup.branding.facade.attendance.AttendanceFacadeService;
import kr.mashup.branding.security.MemberAuth;
import kr.mashup.branding.ui.ApiResponse;
import kr.mashup.branding.ui.attendance.response.AttendanceCheckResponse;
import kr.mashup.branding.ui.attendance.response.PlatformAttendanceResponse;
import kr.mashup.branding.ui.attendance.response.TotalAttendanceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/attendance")
public class AttendanceController {

    private final AttendanceFacadeService attendanceFacadeService;

    @ApiOperation("출석 체크")
    @PostMapping("/check")
    public ApiResponse<AttendanceCheckResponse> check(
            @ApiIgnore MemberAuth auth,
            @RequestParam String checkingCode
    ) {
        AttendanceCheckResponse res = attendanceFacadeService.checkAttendance(
            auth.getMemberId(),
            checkingCode
        );
        return ApiResponse.success(res);
    }

    @ApiOperation("플랫폼 전체 출석조회")
    @GetMapping("/platforms")
    public ApiResponse<TotalAttendanceResponse> getTotalPlatform(
        @RequestParam Long scheduleId
    ) {
        TotalAttendanceResponse res =
            attendanceFacadeService.getTotalAttendance(scheduleId);
        return ApiResponse.success(res);
    }

    @ApiOperation("플랫폼별 출석조회")
    @GetMapping("/platforms/{platformName}")
    public ApiResponse<PlatformAttendanceResponse> getPlatform(
        @PathVariable Platform platformName,
        @RequestParam Long scheduleId
    ) {
        PlatformAttendanceResponse res =
            attendanceFacadeService.getPlatformAttendance(platformName, scheduleId);
        return ApiResponse.success(res);
    }
}
