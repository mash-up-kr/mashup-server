package kr.mashup.branding.facade.schedule;

import java.util.List;

import kr.mashup.branding.domain.schedule.RecruitmentSchedule;
import kr.mashup.branding.domain.schedule.RecruitmentScheduleCreateVo;
import kr.mashup.branding.domain.schedule.RecruitmentScheduleUpdateVo;

public interface RecruitmentScheduleFacadeService {
    List<RecruitmentSchedule> getAll();

    RecruitmentSchedule create(RecruitmentScheduleCreateVo recruitmentScheduleCreateVo);

    RecruitmentSchedule update(Long recruitmentScheduleId, RecruitmentScheduleUpdateVo recruitmentScheduleUpdateVo);

    void delete(Long recruitmentScheduleId);
}
