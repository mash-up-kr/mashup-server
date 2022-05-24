package kr.mashup.branding.domain.attendance;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Attendance extends BaseEntity{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    private AttendanceStatus status;

    //TODO: Event 단위로 출첵하는게 맞을듯?
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="content_id")
    private Content content;
}
