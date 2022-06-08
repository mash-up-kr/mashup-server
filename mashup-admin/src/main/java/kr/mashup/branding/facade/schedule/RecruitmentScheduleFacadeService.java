package kr.mashup.branding.facade.schedule;

import java.util.List;

import kr.mashup.branding.domain.recruitmentschedule.RecruitmentSchedule;
import kr.mashup.branding.domain.recruitmentschedule.RecruitmentScheduleCreateVo;
import kr.mashup.branding.domain.recruitmentschedule.RecruitmentScheduleUpdateVo;

public interface RecruitmentScheduleFacadeService {
    List<RecruitmentSchedule> getAll();

    RecruitmentSchedule create(RecruitmentScheduleCreateVo recruitmentScheduleCreateVo);

    RecruitmentSchedule update(Long recruitmentScheduleId, RecruitmentScheduleUpdateVo recruitmentScheduleUpdateVo);

    void delete(Long recruitmentScheduleId);
}
