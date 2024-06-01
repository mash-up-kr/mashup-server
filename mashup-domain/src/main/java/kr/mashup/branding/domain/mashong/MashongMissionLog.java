package kr.mashup.branding.domain.mashong;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MashongMissionLog {
    @Id
    @GeneratedValue
    private Long id;

    private Long memberGenerationId;

    private Long missionLevelId;

    private Long level;

    private Long missionId;

    private LocalDateTime achievedAt;

    public static MashongMissionLog of(
        Long memberGenerationId,
        MashongMissionLevel mashongMissionLevel,
        LocalDateTime achievedAt
    ) {
        return new MashongMissionLog(memberGenerationId, mashongMissionLevel.getId(), mashongMissionLevel.getLevel(), mashongMissionLevel.getMissionId(), achievedAt);
    }

    private MashongMissionLog(
        Long memberGenerationId,
        Long missionLevelId,
        Long level,
        Long missionId,
        LocalDateTime achievedAt
    ) {
        this.memberGenerationId = memberGenerationId;
        this.missionLevelId = missionLevelId;
        this.level = level;
        this.missionId = missionId;
        this.achievedAt = achievedAt;
    }
}
