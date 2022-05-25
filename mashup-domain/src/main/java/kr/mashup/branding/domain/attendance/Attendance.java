package kr.mashup.branding.domain.attendance;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Attendance extends BaseEntity{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    private AttendanceStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="event_id")
    private Event event;

    public Attendance(Member member, AttendanceStatus status, Event event){
        Assert.notNull(member, "");
        Assert.notNull(status, "");
        Assert.notNull(event, "");

        this.member = member;
        this.status = status;
        this.event = event;
    }

    public void changeStatus(AttendanceStatus status){
        this.status = status;
    }
}
