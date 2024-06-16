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

    private String baseDate;

    private Long level;

    private Long missionId;

    private Long currentStatus;

    private LocalDateTime achievedAt;

    private Boolean isCompensated;

    public static MashongMissionLog of(
        Long memberGenerationId,
        MashongMissionLevel mashongMissionLevel
    ) {
        return new MashongMissionLog(memberGenerationId, mashongMissionLevel.getId(), null, mashongMissionLevel.getLevel(), mashongMissionLevel.getMashongMission().getId(), 0L, null, false);
    }

    public static MashongMissionLog of(
        Long memberGenerationId,
        MashongMissionLevel mashongMissionLevel,
        String baseDate
    ) {
        return new MashongMissionLog(memberGenerationId, mashongMissionLevel.getId(), baseDate, mashongMissionLevel.getLevel(), mashongMissionLevel.getMashongMission().getId(), 0L, null, false);
    }

    public void incrementCurrentStatus(
        Long value
    ) {
        this.currentStatus += value;
    }

    public void compensated() {
        this.achievedAt = LocalDateTime.now();
        this.isCompensated = true;
    }

    private MashongMissionLog(
        Long memberGenerationId,
        Long missionLevelId,
        String baseDate,
        Long level,
        Long missionId,
        Long currentStatus,
        LocalDateTime achievedAt,
        Boolean isCompensated
    ) {
        this.memberGenerationId = memberGenerationId;
        this.missionLevelId = missionLevelId;
        this.baseDate = baseDate;
        this.level = level;
        this.missionId = missionId;
        this.currentStatus = currentStatus;
        this.achievedAt = achievedAt;
        this.isCompensated = isCompensated;
    }
}
