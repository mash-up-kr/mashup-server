package kr.mashup.branding.domain.attendance;

import kr.mashup.branding.util.DateUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AttendanceCode extends BaseEntity{

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event event;

    private String code;

    private LocalDateTime startedAt;

    private LocalDateTime endedAt;

    public AttendanceCode(Event event, String code, LocalDateTime startedAt, LocalDateTime endedAt){
        Assert.notNull(event,"");
        Assert.notNull(startedAt,"");
        Assert.notNull(endedAt,"");
        Assert.hasText(code, "");

        checkStartBeforeOrEqualEnd(startedAt, endedAt);
        checkEventDateContainsCodeDate(event, startedAt, endedAt);

        this.event = event;
        event.setAttendanceCode(this);
        this.code = code;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
    }

    private void checkEventDateContainsCodeDate(Event event, LocalDateTime startedAt, LocalDateTime endedAt){

        LocalDateTime eventStartedAt = event.getStartedAt();
        LocalDateTime eventEndedAt = event.getEndedAt();

        if(!DateUtil.isContainDateRange(eventStartedAt, eventEndedAt, startedAt, endedAt)){
            throw new IllegalArgumentException();
        }
    }


    private void checkStartBeforeOrEqualEnd(LocalDateTime startedAt, LocalDateTime endedAt) {
        if(!DateUtil.isStartBeforeOrEqualEnd(startedAt, endedAt)){
            throw new IllegalArgumentException();
        }
    }

}
