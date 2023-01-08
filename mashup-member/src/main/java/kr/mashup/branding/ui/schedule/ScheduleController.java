package kr.mashup.branding.ui.schedule;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import kr.mashup.branding.facade.schedule.ScheduleFacadeService;
import kr.mashup.branding.ui.ApiResponse;
import kr.mashup.branding.ui.schedule.response.ScheduleResponse;
import kr.mashup.branding.ui.schedule.response.ScheduleResponseList;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v1/schedules")
@RestController
public class ScheduleController {

    private final ScheduleFacadeService scheduleFacadeService;

    @ApiOperation(
        value = "스케줄 조회",
        notes =
            "<h2> Error Code</h2>" +
                "<p>" +
                "SCHEDULE_NOT_FOUND" +
                "</p>"
    )
    @GetMapping("/{id}")
    public ApiResponse<ScheduleResponse> getById(@PathVariable Long id) {
        final ScheduleResponse res
                = scheduleFacadeService.getById(id);

        return ApiResponse.success(res);
    }

    @ApiOperation(
        value = "기수로 스케줄 조회",
        notes =
            "<h2> Error Code</h2>" +
                "<p>" +
                "GENERATION_NOT_FOUND</br>" +
                "SCHEDULE_NOT_FOUND" +
                "</p>"
    )
    @GetMapping("/generations/{generationNumber}")
    public ApiResponse<ScheduleResponseList> getByGenerationNumber(
        @PathVariable Integer generationNumber
    ) {
        final ScheduleResponseList res =
            scheduleFacadeService.getByGenerationNum(generationNumber);

        return ApiResponse.success(res);
    }
}
