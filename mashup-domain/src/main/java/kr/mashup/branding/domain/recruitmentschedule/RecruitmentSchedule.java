package kr.mashup.branding.domain.recruitmentschedule;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.recruitmentschedule.vo.RecruitmentScheduleCreateVo;
import kr.mashup.branding.domain.recruitmentschedule.vo.RecruitmentScheduleUpdateVo;
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
@ToString(of = {"recruitmentScheduleId", "eventName", "eventOccurredAt"})
@EqualsAndHashCode(of = "recruitmentScheduleId")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class RecruitmentSchedule {
    @Id
    @GeneratedValue
    private Long recruitmentScheduleId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name ="generation_id")
    private Generation generation;

    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    private RecruitmentScheduleEventName eventName;

    private LocalDateTime eventOccurredAt;

    @CreatedBy
    private String createdBy;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedBy
    private String updatedBy;

    @LastModifiedDate
    private LocalDateTime updatedAt;


    public static RecruitmentSchedule from(final RecruitmentScheduleCreateVo recruitmentScheduleCreateVo) {
        final RecruitmentSchedule recruitmentSchedule = new RecruitmentSchedule();
        recruitmentSchedule.eventName = recruitmentScheduleCreateVo.getEventName();
        recruitmentSchedule.eventOccurredAt = recruitmentScheduleCreateVo.getEventOccurredAt();
        return recruitmentSchedule;
    }

    public RecruitmentSchedule update(final RecruitmentScheduleUpdateVo recruitmentScheduleUpdateVo) {
        this.eventOccurredAt = recruitmentScheduleUpdateVo.getEventOccurredAt();
        return this;
    }
}
