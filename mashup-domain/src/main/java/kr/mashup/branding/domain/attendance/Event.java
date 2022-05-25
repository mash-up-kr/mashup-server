package kr.mashup.branding.domain.attendance;

import kr.mashup.branding.util.DateUtil;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Event extends BaseEntity{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    private LocalDateTime startedAt;

    private LocalDateTime endedAt;

    @OneToMany(mappedBy = "event")
    private List<Content> contentList;

    @OneToOne(mappedBy = "event", fetch = FetchType.LAZY)
    private AttendanceCode attendanceCode;

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

    public void addContent(Content content){
        if(contentList.contains(content)){
            return;
        }
        this.contentList.add(content);
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

    public void setAttendanceCode(AttendanceCode code){
        this.attendanceCode = code;
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
