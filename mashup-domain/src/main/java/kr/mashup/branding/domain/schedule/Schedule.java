package kr.mashup.branding.domain.schedule;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.validation.constraints.NotNull;

import kr.mashup.branding.domain.schedule.exception.ScheduleAlreadyPublishedException;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.util.Assert;

import kr.mashup.branding.domain.BaseEntity;
import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.util.DateRange;
import kr.mashup.branding.util.DateUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule extends BaseEntity {

    @NotNull
    private String name;

    @NotNull
    private LocalDateTime startedAt;

    @NotNull
    private LocalDateTime endedAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "generation_id")
    private Generation generation;

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("startedAt")
    private List<Event> eventList = new ArrayList<>();

    /*
    ScoreHistory 배치가 수행된 스케줄인지의 여부를 판단하기 위한 컬럼
     */
    private Boolean isCounted;

    @Enumerated(EnumType.STRING)
    private ScheduleStatus status;

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String updatedBy;

    public static Schedule of(Generation generation, String name, DateRange dateRange) {
        return new Schedule(generation, name, dateRange);
    }

    public Schedule(Generation generation, String name, DateRange dateRange) {
        checkStartBeforeOrEqualEnd(dateRange.getStart(), dateRange.getEnd());

        this.generation = generation;
        this.name = name;
        this.startedAt = dateRange.getStart();
        this.endedAt = dateRange.getEnd();
        this.status = ScheduleStatus.ADMIN_ONLY;
        this.isCounted = false; // 기본값은 false 로 설정(배치가 수행되지 않음)
    }

    public void publishSchedule(){
        if(status == ScheduleStatus.ADMIN_ONLY){
            throw new ScheduleAlreadyPublishedException();
        }
        this.status = ScheduleStatus.PUBLIC;
    }

    public void hide(){
        if(status != ScheduleStatus.PUBLIC){

        }
        if(startedAt.isBefore(LocalDateTime.now())){

        }
        this.status = ScheduleStatus.ADMIN_ONLY;
    }


    public void configureEvents(List<Event> events){
        this.eventList = events;
    }

    public void addEvent(Event event){
        this.eventList.add(event);
    }

    public void changeName(String newName) {
        Assert.hasText(newName, "이름이 비어있을 수 없습니다.");
        this.name = newName;
    }

    public void changeStartDate(LocalDateTime newStartDate) {
        checkStartBeforeOrEqualEnd(newStartDate, endedAt);
        this.startedAt = newStartDate;
    }

    public void changeEndDate(LocalDateTime newEndDate) {
        checkStartBeforeOrEqualEnd(startedAt, newEndDate);
        this.endedAt = newEndDate;
    }

    private void checkStartBeforeOrEqualEnd(LocalDateTime startedAt, LocalDateTime endedAt) {
        if (!DateUtil.isStartBeforeOrEqualEnd(startedAt, endedAt)) {
            throw new IllegalArgumentException("유효하지 않은 시작시간과 끝나는 시간입니다.");
        }
    }

    public void changeIsCounted(Boolean isCounted) {
        this.isCounted = isCounted;
    }


}
