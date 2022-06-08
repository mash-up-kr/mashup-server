package kr.mashup.branding.facade.schedule;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.mashup.branding.domain.recruitmentschedule.RecruitmentSchedule;
import kr.mashup.branding.domain.recruitmentschedule.RecruitmentScheduleCreateVo;
import kr.mashup.branding.service.recruitmentschedule.RecruitmentScheduleService;
import kr.mashup.branding.domain.recruitmentschedule.RecruitmentScheduleUpdateVo;
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
