package kr.mashup.branding.domain.attendance;

import kr.mashup.branding.domain.BaseEntity;
import kr.mashup.branding.domain.schedule.Event;
import kr.mashup.branding.util.DateRange;
import kr.mashup.branding.util.DateUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AttendanceCode extends BaseEntity {

    @NotNull
    private String code;
    @NotNull
    private LocalDateTime attendanceCheckStartedAt;     // 출석체크 시작시간
    @NotNull
    private LocalDateTime attendanceCheckEndedAt;       // 출석체크 마감시간

    @NotNull
    private LocalDateTime latenessCheckStartedAt;       // 지각체크 시작시간

    @NotNull
    private LocalDateTime latenessCheckEndedAt;         // 지각체크 마감시간

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "event_id")
    private Event event;

    public static AttendanceCode of(Event event, String code, DateRange attendanceTime, DateRange lateTime) {
        return new AttendanceCode(event, code, attendanceTime, lateTime);
    }

    public void changeAttendanceTime(DateRange attendanceTime){
        this.attendanceCheckStartedAt = attendanceTime.getStart();
        this.attendanceCheckEndedAt = attendanceTime.getEnd();
    }

    public void changeLateTime(DateRange lateTime){
        this.latenessCheckStartedAt = lateTime.getStart();
        this.latenessCheckEndedAt = lateTime.getEnd();
    }

    private AttendanceCode(Event event, String code, DateRange attendanceTime, DateRange lateTime) {

        this.event = event;
        this.code = code;
        this.attendanceCheckStartedAt = attendanceTime.getStart();
        this.attendanceCheckEndedAt = attendanceTime.getEnd();
        this.latenessCheckStartedAt = lateTime.getStart();
        this.latenessCheckEndedAt = lateTime.getEnd();
    }

}
