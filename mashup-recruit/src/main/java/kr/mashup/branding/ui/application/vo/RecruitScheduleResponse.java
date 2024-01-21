package kr.mashup.branding.ui.application.vo;

import kr.mashup.branding.domain.recruitmentschedule.RecruitmentSchedule;
import kr.mashup.branding.domain.recruitmentschedule.RecruitmentScheduleEventName;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RecruitScheduleResponse {

    private Long recruitScheduleId;
    private RecruitmentScheduleEventName eventName;

    private LocalDateTime eventOccurredAt;

    public static RecruitScheduleResponse of(final RecruitmentSchedule schedule){
        return new RecruitScheduleResponse(schedule.getRecruitmentScheduleId(), schedule.getEventName(), schedule.getEventOccurredAt());
    }
}
