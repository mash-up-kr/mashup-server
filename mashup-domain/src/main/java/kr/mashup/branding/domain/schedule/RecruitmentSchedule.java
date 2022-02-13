package kr.mashup.branding.domain.schedule;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@EqualsAndHashCode(of = "recruitmentScheduleId")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class RecruitmentSchedule {
    @Id
    @GeneratedValue
    private Long recruitmentScheduleId;

    @Column(unique = true)
    private String eventName;

    private LocalDateTime eventOccurredAt;

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String updatedBy;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    /**
     * 12기 모집 시작 시각 (inclusive)
     */
    private static final RecruitmentSchedule RECRUITMENT_STARTED;

    /**
     * 12기 모집 종료 날짜 (inclusive)
     */
    private static final RecruitmentSchedule RECRUITMENT_ENDED;

    /**
     * 12기 서류 결과 발표 시각 (inclusive)
     */
    private static final RecruitmentSchedule SCREENING_RESULT_ANNOUNCED;

    /**
     * 12기 면접 결과 발표 시각 (inclusive)
     */
    private static final RecruitmentSchedule INTERVIEW_RESULT_ANNOUNCED;

    static {
        RECRUITMENT_STARTED = new RecruitmentSchedule();
        RECRUITMENT_STARTED.eventName = "RECRUITMENT_STARTED";
        RECRUITMENT_STARTED.eventOccurredAt = LocalDateTime.of(2022, 3, 2, 0, 0);

        RECRUITMENT_ENDED = new RecruitmentSchedule();
        RECRUITMENT_ENDED.eventName = "RECRUITMENT_ENDED";
        RECRUITMENT_ENDED.eventOccurredAt = LocalDateTime.of(2022, 3, 2, 0, 0);

        SCREENING_RESULT_ANNOUNCED = new RecruitmentSchedule();
        SCREENING_RESULT_ANNOUNCED.eventName = "SCREENING_RESULT_ANNOUNCED";
        SCREENING_RESULT_ANNOUNCED.eventOccurredAt = LocalDateTime.of(2022, 3, 2, 0, 0);

        INTERVIEW_RESULT_ANNOUNCED = new RecruitmentSchedule();
        INTERVIEW_RESULT_ANNOUNCED.eventName = "INTERVIEW_RESULT_ANNOUNCED";
        INTERVIEW_RESULT_ANNOUNCED.eventOccurredAt = LocalDateTime.of(2022, 3, 2, 0, 0);
    }

    public static List<RecruitmentSchedule> get12thRecruitSchedules() {
        return Arrays.asList(
            RECRUITMENT_STARTED,
            RECRUITMENT_ENDED,
            SCREENING_RESULT_ANNOUNCED,
            INTERVIEW_RESULT_ANNOUNCED
        );
    }

    public static RecruitmentSchedule from(RecruitmentScheduleCreateVo recruitmentScheduleCreateVo) {
        RecruitmentSchedule recruitmentSchedule = new RecruitmentSchedule();
        recruitmentSchedule.eventName = recruitmentScheduleCreateVo.getEventName();
        recruitmentSchedule.eventOccurredAt = recruitmentScheduleCreateVo.getEventOccurredAt();
        return recruitmentSchedule;
    }

    RecruitmentSchedule update(RecruitmentScheduleUpdateVo recruitmentScheduleUpdateVo) {
        this.eventOccurredAt = recruitmentScheduleUpdateVo.getEventOccurredAt();
        return this;
    }
}
