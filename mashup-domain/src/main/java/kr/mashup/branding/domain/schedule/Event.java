package kr.mashup.branding.domain.schedule;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.validation.constraints.NotBlank;

import com.sun.istack.NotNull;

import kr.mashup.branding.domain.BaseEntity;
import kr.mashup.branding.domain.attendance.AttendanceCode;
import kr.mashup.branding.domain.schedule.exception.EventInvalidNameException;
import kr.mashup.branding.util.DateRange;
import kr.mashup.branding.util.DateUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Event extends BaseEntity {

    @NotBlank
    private String eventName;

    @NotNull
    private LocalDateTime startedAt;

    @NotNull
    private LocalDateTime endedAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("startedAt")
    private final List<Content> contentList = new ArrayList<>();

    @OneToOne// optional true 이다.
    @JoinColumn(name = "attendance_code_id")
    private AttendanceCode attendanceCode;

    public static Event of(Schedule schedule,String eventName, DateRange dateRange) {
        return new Event(schedule, eventName, dateRange);
    }

    private Event(Schedule schedule,String eventName, DateRange dateRange) {
        validateEventPeriod(schedule, dateRange.getStart(), dateRange.getEnd());
        validateEventName(eventName);
        this.eventName = eventName;
        this.schedule = schedule;
        this.startedAt = dateRange.getStart();
        this.endedAt = dateRange.getEnd();
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
        validateEventPeriod(schedule, newStartedAt, endedAt);
        this.startedAt = newStartedAt;
    }

    public void changeEndDate(LocalDateTime newEndedAt) {
        checkStartBeforeOrEqualEnd(startedAt, newEndedAt);
        validateEventPeriod(schedule, startedAt, newEndedAt);
        this.endedAt = newEndedAt;
    }

    private void validateEventName(String eventName){
        if(!StringUtils.hasText(eventName)){
            throw new EventInvalidNameException();
        }
    }

    private void validateEventPeriod(Schedule schedule, LocalDateTime startedAt, LocalDateTime endedAt) {
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
