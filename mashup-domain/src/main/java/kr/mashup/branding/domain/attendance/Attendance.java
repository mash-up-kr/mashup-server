package kr.mashup.branding.domain.attendance;

import javax.persistence.*;

@Entity
public class Attendance extends BaseEntity{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    private AttendanceStatus status;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="content_id")
    private Content content;
}
