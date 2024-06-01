package kr.mashup.branding.domain.mashong;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MashongAttendance {
    @Id
    @GeneratedValue
    private Long id;

    private Long memberGenerationId;

    private LocalDateTime attendanceAt;

    public static MashongAttendance of(
        Long memberGenerationId,
        LocalDateTime attendanceAt
    ) {
        return new MashongAttendance(memberGenerationId, attendanceAt);
    }

    private MashongAttendance(
        Long memberGenerationId,
        LocalDateTime attendanceAt
    ) {
        this.memberGenerationId = memberGenerationId;
        this.attendanceAt = attendanceAt;
    }
}
