package kr.mashup.branding.ui.recruitmentschedule;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.mashup.branding.facade.recruitmentschedule.RecruitmentScheduleFacadeService;
import kr.mashup.branding.ui.ApiResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/recruitment-schedules")
@RequiredArgsConstructor
public class RecruitmentScheduleController {

    private final RecruitmentScheduleFacadeService recruitmentScheduleFacadeService;

    @GetMapping
    public ApiResponse<List<RecruitmentScheduleResponse>> getSchedules(
        @RequestParam(defaultValue = "13", required = false) Integer generationNumber
    ) {
        final List<RecruitmentScheduleResponse> responses
            = recruitmentScheduleFacadeService.getAll(generationNumber);

        return ApiResponse.success(responses);
    }

    @PostMapping
    public ApiResponse<RecruitmentScheduleResponse> create(
        @RequestParam(defaultValue = "13", required = false) Integer generationNumber,
        @RequestBody RecruitmentScheduleCreateRequest request
    ) {
        final RecruitmentScheduleResponse response
            = recruitmentScheduleFacadeService.create(generationNumber, request);

        return ApiResponse.success(response);
    }

    @PutMapping("/{recruitmentScheduleId}")
    public ApiResponse<RecruitmentScheduleResponse> update(
        @PathVariable Long recruitmentScheduleId,
        @RequestBody RecruitmentScheduleUpdateRequest request
    ) {

        RecruitmentScheduleResponse response
            = recruitmentScheduleFacadeService.update(recruitmentScheduleId, request);

        return ApiResponse.success(response);
    }

    @DeleteMapping("/{recruitmentScheduleId}")
    public ApiResponse<?> delete(@PathVariable Long recruitmentScheduleId) {
        recruitmentScheduleFacadeService.delete(recruitmentScheduleId);
        return ApiResponse.success();
    }
}
