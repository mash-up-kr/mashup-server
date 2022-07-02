package kr.mashup.branding.ui.attendance;

import kr.mashup.branding.facade.attendance.AttendanceFacadeService;
import kr.mashup.branding.ui.ApiResponse;
import kr.mashup.branding.ui.attendance.reqeust.AttendanceCheckRequest;
import kr.mashup.branding.ui.attendance.response.AttendanceCheckResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/attendance")
public class AttendanceController {

    private final AttendanceFacadeService attendanceFacadeService;

    @PostMapping("/check")
    public ApiResponse<AttendanceCheckResponse> check(
        @RequestBody AttendanceCheckRequest req
    ) {
        AttendanceCheckResponse res = attendanceFacadeService.checkAttendance(req);
        return ApiResponse.success(res);
    }
}
