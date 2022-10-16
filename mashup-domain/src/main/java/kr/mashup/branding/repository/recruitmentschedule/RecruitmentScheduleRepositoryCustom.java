package kr.mashup.branding.repository.recruitmentschedule;

import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.recruitmentschedule.RecruitmentSchedule;
import kr.mashup.branding.domain.recruitmentschedule.RecruitmentScheduleEventName;

import java.util.List;
import java.util.Optional;

public interface RecruitmentScheduleRepositoryCustom {
    List<RecruitmentSchedule> findAllByGeneration(Generation generation);
    Optional<RecruitmentSchedule> findByEventName(Generation generation, RecruitmentScheduleEventName eventName);

}
