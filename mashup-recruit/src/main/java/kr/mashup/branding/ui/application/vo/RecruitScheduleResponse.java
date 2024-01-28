package kr.mashup.branding.ui.application.vo;

import kr.mashup.branding.domain.recruitmentschedule.RecruitmentSchedule;
import kr.mashup.branding.domain.recruitmentschedule.RecruitmentScheduleEventName;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RecruitScheduleResponse {

    private Long recruitScheduleId;
    private RecruitmentScheduleEventName eventName;
    private ZonedDateTime eventOccurredAt;

    public static RecruitScheduleResponse of(final RecruitmentSchedule schedule){
        return new RecruitScheduleResponse(
                schedule.getRecruitmentScheduleId(),
                schedule.getEventName(),
                convertToZonedDateTime(schedule.getEventOccurredAt()));
    }


    private static ZonedDateTime convertToZonedDateTime(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return ZonedDateTime.of(localDateTime, ZoneId.of("Asia/Seoul"));
    }
}
