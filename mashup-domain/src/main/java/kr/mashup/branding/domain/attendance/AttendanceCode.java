package kr.mashup.branding.domain.attendance;

import kr.mashup.branding.util.DateUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AttendanceCode extends BaseEntity{

    @NotNull
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "event_id")
    private Event event;
    @NotNull
    private String code;
    @NotNull
    private LocalDateTime startedAt;
    @NotNull
    private LocalDateTime endedAt;

    public static AttendanceCode of(Event event, String code, LocalDateTime startedAt, LocalDateTime endedAt){
        return new AttendanceCode(event, code, startedAt, endedAt);
    }

    private AttendanceCode(Event event, String code, LocalDateTime startedAt, LocalDateTime endedAt){

        checkStartBeforeOrEqualEnd(startedAt, endedAt);
        checkAttendancePeriod(event, startedAt, endedAt);

        this.event = event;
        event.setAttendanceCode(this);
        this.code = code;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
    }

    private void checkStartBeforeOrEqualEnd(LocalDateTime startedAt, LocalDateTime endedAt) {
        if(!DateUtil.isStartBeforeOrEqualEnd(startedAt, endedAt)){
            throw new IllegalArgumentException();
        }
    }

    private void checkAttendancePeriod(Event event, LocalDateTime startedAt, LocalDateTime endedAt){

        LocalDateTime eventStartedAt = event.getStartedAt();
        LocalDateTime eventEndedAt = event.getEndedAt();

        if(!DateUtil.isContainDateRange(eventStartedAt, eventEndedAt, startedAt, endedAt)){
            throw new IllegalArgumentException();
        }
    }




}
