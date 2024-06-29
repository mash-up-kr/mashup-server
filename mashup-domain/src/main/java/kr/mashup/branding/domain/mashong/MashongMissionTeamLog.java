package kr.mashup.branding.domain.mashong;

import kr.mashup.branding.domain.member.Platform;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MashongMissionTeamLog {
    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private Platform platform;

    private Long generationId;

    private Long missionLevelId;

    private String baseDate;

    private Long level;

    private Long missionId;

    @Setter
    private Double currentStatus;

    private LocalDateTime achievedAt;

    private Boolean isCompensated;

    public static MashongMissionTeamLog of(
        Platform platform,
        Long generationId,
        MashongMissionLevel mashongMissionLevel
    ) {
        return new MashongMissionTeamLog(platform, generationId, mashongMissionLevel.getId(), null, mashongMissionLevel.getLevel(), mashongMissionLevel.getMashongMission().getId(), 0.0, null, false);
    }

    public static MashongMissionTeamLog of(
        Platform platform,
        Long generationId,
        MashongMissionLevel mashongMissionLevel,
        String baseDate
    ) {
        return new MashongMissionTeamLog(platform, generationId, mashongMissionLevel.getId(), baseDate, mashongMissionLevel.getLevel(), mashongMissionLevel.getMashongMission().getId(), 0.0, null, false);
    }

    public void incrementCurrentStatus(
        Double value
    ) {
        this.currentStatus += value;
    }

    public void compensated() {
        this.achievedAt = LocalDateTime.now();
        this.isCompensated = true;
    }

    private MashongMissionTeamLog(
        Platform platform,
        Long generationId,
        Long missionLevelId,
        String baseDate,
        Long level,
        Long missionId,
        Double currentStatus,
        LocalDateTime achievedAt,
        Boolean isCompensated
    ) {
        this.platform = platform;
        this.generationId = generationId;
        this.missionLevelId = missionLevelId;
        this.baseDate = baseDate;
        this.level = level;
        this.missionId = missionId;
        this.currentStatus = currentStatus;
        this.achievedAt = achievedAt;
        this.isCompensated = isCompensated;
    }
}
