package kr.mashup.branding.facade.schedule;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.mashup.branding.domain.schedule.RecruitmentEvent;
import kr.mashup.branding.domain.schedule.RecruitmentSchedule;
import kr.mashup.branding.domain.schedule.RecruitmentScheduleService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecruitmentScheduleFacadeServiceImpl implements RecruitmentScheduleFacadeService {
    private final RecruitmentScheduleService recruitmentScheduleService;

    @Override
    public List<RecruitmentSchedule> getAll() {
        return recruitmentScheduleService.getAll();
    }

    @Override
    public RecruitmentSchedule createOrUpdate(RecruitmentEvent recruitmentEvent) {
        return recruitmentScheduleService.createOrUpdate(recruitmentEvent);
    }
}
