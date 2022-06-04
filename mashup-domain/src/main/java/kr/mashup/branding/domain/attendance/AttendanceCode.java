package kr.mashup.branding.domain.attendance;

import kr.mashup.branding.util.DateRange;
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

    public static AttendanceCode of(Event event, String code, DateRange dateRange){
        return new AttendanceCode(event, code, dateRange);
    }

    private AttendanceCode(Event event, String code, DateRange dateRange){

        checkAttendancePeriod(event, dateRange);

        this.event = event;
        event.setAttendanceCode(this);
        this.code = code;
        this.startedAt = dateRange.getStart();
        this.endedAt = dateRange.getEnd();
    }


    private void checkAttendancePeriod(Event event, DateRange dateRange){

        if(!DateUtil.isContainDateRange(DateRange.of(event.getStartedAt(), event.getEndedAt()), dateRange)){
            throw new IllegalArgumentException();
        }
    }




}
