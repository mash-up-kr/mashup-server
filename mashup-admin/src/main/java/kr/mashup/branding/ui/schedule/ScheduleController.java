package kr.mashup.branding.ui.schedule;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import kr.mashup.branding.facade.schedule.ScheduleFacadeService;
import kr.mashup.branding.ui.ApiResponse;
import kr.mashup.branding.ui.schedule.request.ScheduleCreateRequest;
import kr.mashup.branding.ui.schedule.response.ScheduleResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v1/schedules")
@RestController
public class ScheduleController {

    private final ScheduleFacadeService scheduleFacadeService;

    @ApiOperation("스케줄 생성")
    @PostMapping("/")
    public ApiResponse<ScheduleResponse> create(
        @Valid @RequestBody ScheduleCreateRequest scheduleCreateRequest
    ) {
        ScheduleResponse res = scheduleFacadeService.create(scheduleCreateRequest);

        return ApiResponse.success(res);
    }
}
