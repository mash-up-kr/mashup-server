package kr.mashup.branding.facade.schedule;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.mashup.branding.domain.schedule.RecruitmentSchedule;
import kr.mashup.branding.domain.schedule.RecruitmentScheduleCreateVo;
import kr.mashup.branding.domain.schedule.RecruitmentScheduleService;
import kr.mashup.branding.domain.schedule.RecruitmentScheduleUpdateVo;
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
    public RecruitmentSchedule create(RecruitmentScheduleCreateVo recruitmentScheduleCreateVo) {
        return recruitmentScheduleService.create(recruitmentScheduleCreateVo);
    }

    @Override
    public RecruitmentSchedule update(
        Long recruitmentScheduleId,
        RecruitmentScheduleUpdateVo recruitmentScheduleUpdateVo
    ) {
        return recruitmentScheduleService.update(recruitmentScheduleId, recruitmentScheduleUpdateVo);
    }

    @Override
    public void delete(Long recruitmentScheduleId) {
        recruitmentScheduleService.delete(recruitmentScheduleId);
    }
}
