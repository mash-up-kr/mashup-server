package kr.mashup.branding.ui.schedule;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import kr.mashup.branding.facade.schedule.ScheduleFacadeService;
import kr.mashup.branding.ui.ApiResponse;
import kr.mashup.branding.ui.schedule.response.ScheduleResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v1/schedules")
@RestController
public class ScheduleController {

    private final ScheduleFacadeService scheduleFacadeService;

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
