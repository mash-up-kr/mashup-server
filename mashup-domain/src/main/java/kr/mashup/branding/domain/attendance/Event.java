package kr.mashup.branding.domain.attendance;

import com.sun.istack.NotNull;
import kr.mashup.branding.util.DateUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Event extends BaseEntity{

    @NotNull
    private LocalDateTime startedAt;

    @NotNull
    private LocalDateTime endedAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @OneToMany(mappedBy = "event")
    private final List<Content> contentList = new ArrayList<>();

    @OneToOne(mappedBy = "event", fetch = FetchType.LAZY)
    private AttendanceCode attendanceCode;


    public static Event of(Schedule schedule, LocalDateTime startedAt, LocalDateTime endedAt){
        return new Event(schedule, startedAt, endedAt);
    }

    private Event(Schedule schedule, LocalDateTime startedAt, LocalDateTime endedAt){

        checkStartBeforeOrEqualEnd(startedAt, endedAt);
        checkEventPeriod(schedule, startedAt, endedAt);

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
        checkEventPeriod(schedule, newStartedAt, endedAt);
        this.startedAt = newStartedAt;
    }

    public void changeEndDate(LocalDateTime newEndedAt){
        checkStartBeforeOrEqualEnd(startedAt, newEndedAt);
        checkEventPeriod(schedule, startedAt, newEndedAt);
        this.endedAt = newEndedAt;
    }

    public void setAttendanceCode(AttendanceCode code){
        this.attendanceCode = code;
    }

    private void checkEventPeriod(Schedule schedule, LocalDateTime statedAt, LocalDateTime endedAt) {
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
