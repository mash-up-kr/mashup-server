package kr.mashup.branding.ui.schedule;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import kr.mashup.branding.domain.schedule.Schedule;
import kr.mashup.branding.domain.schedule.ScheduleCreateVo;
import kr.mashup.branding.service.schedule.ScheduleService;
import kr.mashup.branding.ui.ApiResponse;
import kr.mashup.branding.ui.schedule.request.ScheduleCreateRequest;
import kr.mashup.branding.ui.schedule.response.ScheduleResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v1/schedules")
@RestController
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final ScheduleAssembler scheduleAssembler;

    @ApiOperation("스케줄 생성")
    @PostMapping()
    public ApiResponse<ScheduleResponse> create(
        @RequestBody ScheduleCreateRequest scheduleCreateRequest
    ) {
        Schedule schedule = scheduleService.create(
            ScheduleCreateVo.of(
                scheduleCreateRequest.getName(),
                scheduleCreateRequest.getStartedAt(),
                scheduleCreateRequest.getEndedAt(),
                scheduleCreateRequest.getGenerationId())
        );
        return ApiResponse.success(scheduleAssembler.toScheduleResponse(schedule));
    }
}
