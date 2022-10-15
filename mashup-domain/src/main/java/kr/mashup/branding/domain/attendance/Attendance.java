package kr.mashup.branding.domain.attendance;

import com.sun.istack.NotNull;
import kr.mashup.branding.domain.BaseEntity;
import kr.mashup.branding.domain.event.Event;
import kr.mashup.branding.domain.member.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
    uniqueConstraints={
        @UniqueConstraint(
            columnNames={"event_id", "member_id"}
        )
    }
)
public class Attendance extends BaseEntity {

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name ="member_id")
    private Member member;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name ="event_id")
    private Event event;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AttendanceStatus status;


    public static Attendance of(Member member, AttendanceStatus status, Event event){
        return new Attendance(member, status, event);
    }

    private Attendance(Member member, AttendanceStatus status, Event event){

        this.member = member;
        this.status = status;
        this.event = event;
    }

    public void changeStatus(AttendanceStatus status){
        this.status = status;
    }
}
