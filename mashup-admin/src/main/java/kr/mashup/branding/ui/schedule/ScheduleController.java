package kr.mashup.branding.ui.schedule;

import javax.validation.Valid;

import kr.mashup.branding.EmptyResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import kr.mashup.branding.facade.schedule.ScheduleFacadeService;
import kr.mashup.branding.ui.ApiResponse;
import kr.mashup.branding.ui.schedule.request.ScheduleUpdateRequest;
import kr.mashup.branding.ui.schedule.response.ScheduleResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v1/schedules")
@RestController
public class ScheduleController {

    private final ScheduleFacadeService scheduleFacadeService;

    @ApiOperation("스케줄 생성")
    @PostMapping
    public ApiResponse<ScheduleResponse> create(
        @RequestParam(defaultValue = "12", required = false) Integer generationNumber,
        @Valid @RequestBody ScheduleUpdateRequest scheduleUpdateRequest
    ) {
        final ScheduleResponse response
            = scheduleFacadeService.create(generationNumber, scheduleUpdateRequest);

        return ApiResponse.success(response);
    }

    @ApiOperation("스케줄 배포")
    @PostMapping("/{id}")
    public ApiResponse<EmptyResponse> publish(
        @PathVariable("id") Long scheduleId
    ){
        scheduleFacadeService.publishSchedule(scheduleId);

        return ApiResponse.success(EmptyResponse.of());
    }
}
