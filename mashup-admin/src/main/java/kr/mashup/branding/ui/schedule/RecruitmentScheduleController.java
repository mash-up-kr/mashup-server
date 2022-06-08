package kr.mashup.branding.ui.schedule;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.mashup.branding.domain.recruitmentschedule.RecruitmentSchedule;
import kr.mashup.branding.domain.recruitmentschedule.RecruitmentScheduleCreateVo;
import kr.mashup.branding.domain.recruitmentschedule.RecruitmentScheduleUpdateVo;
import kr.mashup.branding.facade.schedule.RecruitmentScheduleFacadeService;
import kr.mashup.branding.ui.ApiResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/recruitment-schedules")
@RequiredArgsConstructor
public class RecruitmentScheduleController {
    private final RecruitmentScheduleFacadeService recruitmentScheduleFacadeService;
    private final RecruitmentScheduleAssembler recruitmentScheduleAssembler;

    @GetMapping
    public ApiResponse<List<RecruitmentScheduleResponse>> getSchedules() {
        return ApiResponse.success(
            recruitmentScheduleFacadeService.getAll()
                .stream()
                .map(recruitmentScheduleAssembler::toRecruitmentScheduleResponse)
                .collect(Collectors.toList())
        );
    }

    @PostMapping
    public ApiResponse<RecruitmentScheduleResponse> create(
        @RequestBody RecruitmentScheduleCreateRequest request
    ) {
        // TODO: 권한 검사 필요함
        RecruitmentScheduleCreateVo createVo = recruitmentScheduleAssembler.toCreateRecruitmentScheduleVo(request);
        RecruitmentSchedule recruitmentSchedule = recruitmentScheduleFacadeService.create(createVo);
        return ApiResponse.success(
            recruitmentScheduleAssembler.toRecruitmentScheduleResponse(recruitmentSchedule)
        );
    }

    @PutMapping("/{recruitmentScheduleId}")
    public ApiResponse<RecruitmentScheduleResponse> update(
        @PathVariable Long recruitmentScheduleId,
        @RequestBody RecruitmentScheduleUpdateRequest request
    ) {
        RecruitmentScheduleUpdateVo updateVo = recruitmentScheduleAssembler.toUpdateRecruitmentScheduleVo(request);
        RecruitmentSchedule recruitmentSchedule = recruitmentScheduleFacadeService.update(recruitmentScheduleId,
            updateVo);
        return ApiResponse.success(
            recruitmentScheduleAssembler.toRecruitmentScheduleResponse(recruitmentSchedule)
        );
    }

    @DeleteMapping("/{recruitmentScheduleId}")
    public ApiResponse<?> delete(@PathVariable Long recruitmentScheduleId) {
        recruitmentScheduleFacadeService.delete(recruitmentScheduleId);
        return ApiResponse.success();
    }
}
