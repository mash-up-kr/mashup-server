package kr.mashup.branding.domain.attendance;

import kr.mashup.branding.util.DateUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Event extends BaseEntity{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    private LocalDateTime startedAt;

    private LocalDateTime endedAt;

    public Event(Schedule schedule, LocalDateTime startedAt, LocalDateTime endedAt){

        Assert.notNull(schedule, "");
        Assert.notNull(startedAt, "");
        Assert.notNull(endedAt, "");

        checkStartBeforeOrEqualEnd(startedAt, endedAt);

        checkScheduleDateContainEventDate(schedule, startedAt, endedAt);

        this.schedule = schedule;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
    }

    public void changeStartDate(LocalDateTime newStartedAt){
        checkStartBeforeOrEqualEnd(newStartedAt, endedAt);
        checkScheduleDateContainEventDate(schedule, newStartedAt, endedAt);
        this.startedAt = newStartedAt;
    }

    public void changeEndDate(LocalDateTime newEndedAt){
        checkStartBeforeOrEqualEnd(startedAt, newEndedAt);
        checkScheduleDateContainEventDate(schedule, startedAt, newEndedAt);
        this.endedAt = newEndedAt;
    }

    private void checkScheduleDateContainEventDate(Schedule schedule, LocalDateTime statedAt, LocalDateTime endedAt) {
        if(!DateUtil.isContainDateRange(schedule.getStartedAt(), schedule.getEndedAt(), statedAt, endedAt)){
            throw new IllegalArgumentException();
        }
    }

    private void checkStartBeforeOrEqualEnd(LocalDateTime statedAt, LocalDateTime endedAt) {
        if(!DateUtil.isStartBeforeOrEqualEnd(statedAt, endedAt)){
            throw new IllegalArgumentException();
        }
    }

}
