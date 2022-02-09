package kr.mashup.branding.ui.schedule;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.mashup.branding.domain.schedule.RecruitmentEvent;
import kr.mashup.branding.domain.schedule.RecruitmentSchedule;
import kr.mashup.branding.facade.schedule.RecruitmentScheduleFacadeService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/recruitment-schedules")
@RequiredArgsConstructor
public class RecruitmentScheduleController {
    private final RecruitmentScheduleFacadeService recruitmentScheduleFacadeService;
    private final RecruitmentScheduleAssembler recruitmentScheduleAssembler;

    @GetMapping
    public List<RecruitmentScheduleResponse> getSchedules() {
        return recruitmentScheduleFacadeService.getAll()
            .stream()
            .map(recruitmentScheduleAssembler::toRecruitmentScheduleResponse)
            .collect(Collectors.toList());
    }

    @PostMapping
    public RecruitmentScheduleResponse createOrUpdate(
        @RequestBody RecruitmentScheduleRequest request
    ) {
        // TODO: 권한 검사 필요함
        RecruitmentEvent recruitmentEvent = recruitmentScheduleAssembler.toRecruitmentEvent(request);
        RecruitmentSchedule recruitmentSchedule = recruitmentScheduleFacadeService.createOrUpdate(recruitmentEvent);
        return recruitmentScheduleAssembler.toRecruitmentScheduleResponse(recruitmentSchedule);
    }
}
