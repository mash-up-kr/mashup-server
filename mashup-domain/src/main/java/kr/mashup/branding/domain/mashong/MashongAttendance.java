package kr.mashup.branding.domain.mashong;

import kr.mashup.branding.domain.member.MemberGeneration;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MashongAttendance {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_generation_id")
    private MemberGeneration memberGeneration;

    private LocalDateTime attendanceAt;

    public static MashongAttendance of(
        MemberGeneration memberGeneration,
        LocalDateTime attendanceAt
    ) {
        return new MashongAttendance(memberGeneration, attendanceAt);
    }

    private MashongAttendance(
        MemberGeneration memberGeneration,
        LocalDateTime attendanceAt
    ) {
        this.memberGeneration = memberGeneration;
        this.attendanceAt = attendanceAt;
    }
}
