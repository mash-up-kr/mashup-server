package kr.mashup.branding.ui.schedule;

import io.swagger.annotations.ApiOperation;
import kr.mashup.branding.facade.schedule.ScheduleFacadeService;
import kr.mashup.branding.service.schedule.ScheduleService;
import kr.mashup.branding.ui.ApiResponse;
import kr.mashup.branding.ui.schedule.request.ScheduleCreateRequest;
import kr.mashup.branding.ui.schedule.response.ScheduleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    @ApiOperation("스케줄 조회")
    @GetMapping("/{id}")
    public ApiResponse<ScheduleResponse> getById(@PathVariable Long id) {
        ScheduleResponse res = scheduleFacadeService.getById(id);

        return ApiResponse.success(res);
    }

    @ApiOperation("기수로 스케줄 조회")
    @GetMapping("/generations/{generationNumber}")
    public ApiResponse<List<ScheduleResponse>> getByGenerationNumber(
        @PathVariable Integer generationNumber
    ) {
        List<ScheduleResponse> res =
            scheduleFacadeService.getByGenerationNum(generationNumber);

        return ApiResponse.success(res);
    }
}
