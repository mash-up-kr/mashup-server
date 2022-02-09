package kr.mashup.branding.facade.schedule;

import java.util.List;

import kr.mashup.branding.domain.schedule.RecruitmentEvent;
import kr.mashup.branding.domain.schedule.RecruitmentSchedule;

public interface RecruitmentScheduleFacadeService {
    List<RecruitmentSchedule> getAll();

    RecruitmentSchedule createOrUpdate(RecruitmentEvent recruitmentEvent);
}
