package kr.mashup.branding.domain.event;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;

import com.sun.istack.NotNull;

import kr.mashup.branding.domain.BaseEntity;
import kr.mashup.branding.domain.attendance.AttendanceCode;
import kr.mashup.branding.domain.content.Content;
import kr.mashup.branding.domain.schedule.Schedule;
import kr.mashup.branding.util.DateRange;
import kr.mashup.branding.util.DateUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Event extends BaseEntity {

    @NotNull
    private LocalDateTime startedAt;

    @NotNull
    private LocalDateTime endedAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @OneToMany(mappedBy = "event")
    @OrderBy("startedAt")
    private final List<Content> contentList = new ArrayList<>();

    @OneToOne// optional true 이다.
    @JoinColumn(name = "attendance_code_id")
    private AttendanceCode attendanceCode;

    public static Event of(Schedule schedule, DateRange dateRange) {
        return new Event(schedule, dateRange);
    }

    private Event(Schedule schedule, DateRange dateRange) {
        checkEventPeriod(schedule, dateRange.getStart(), dateRange.getEnd());
        this.schedule = schedule;
        this.startedAt = dateRange.getStart();
        this.endedAt = dateRange.getEnd();
        schedule.addEvent(this);
    }

    public void addContent(Content content) {
        if (contentList.contains(content)) {
            return;
        }
        this.contentList.add(content);
    }

    public void setAttendanceCode(AttendanceCode code) {
        this.attendanceCode = code;
    }

    public void changeStartDate(LocalDateTime newStartedAt) {
        checkStartBeforeOrEqualEnd(newStartedAt, endedAt);
        checkEventPeriod(schedule, newStartedAt, endedAt);
        this.startedAt = newStartedAt;
    }

    public void changeEndDate(LocalDateTime newEndedAt) {
        checkStartBeforeOrEqualEnd(startedAt, newEndedAt);
        checkEventPeriod(schedule, startedAt, newEndedAt);
        this.endedAt = newEndedAt;
    }

    private void checkEventPeriod(Schedule schedule, LocalDateTime startedAt, LocalDateTime endedAt) {
        if (!DateUtil.isContainDateRange(DateRange.of(schedule.getStartedAt(), schedule.getEndedAt()),
            DateRange.of(startedAt, endedAt))) {
            throw new IllegalArgumentException();
        }
    }

    private void checkStartBeforeOrEqualEnd(LocalDateTime statedAt, LocalDateTime endedAt) {
        if (!DateUtil.isStartBeforeOrEqualEnd(statedAt, endedAt)) {
            throw new IllegalArgumentException();
        }
    }

}
