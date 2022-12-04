package kr.mashup.branding.domain.attendance;

import kr.mashup.branding.domain.BaseEntity;
import kr.mashup.branding.domain.schedule.Event;
import kr.mashup.branding.util.DateRange;
import kr.mashup.branding.util.DateUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AttendanceCode extends BaseEntity {

    @NotNull
    private String code;
    @NotNull
    private LocalDateTime startedAt;
    @NotNull
    private LocalDateTime endedAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "event_id")
    private Event event;

    public static AttendanceCode of(Event event, String code, DateRange dateRange) {
        return new AttendanceCode(event, code, dateRange);
    }


    private AttendanceCode(Event event, String code, DateRange dateRange) {

        checkAttendancePeriod(event, dateRange);
        this.event = event;
        this.code = code;
        this.startedAt = dateRange.getStart();
        this.endedAt = dateRange.getEnd();
    }


    private void checkAttendancePeriod(Event event, DateRange dateRange) {

        if (!DateUtil.isContainDateRange(DateRange.of(event.getStartedAt(), event.getEndedAt()), dateRange)) {
            throw new IllegalArgumentException();
        }
    }


}
