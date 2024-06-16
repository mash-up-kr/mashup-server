package kr.mashup.branding.domain.mashong;

import kr.mashup.branding.domain.member.Platform;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    private Long missionLevelId;

    private String baseDate;

    private Long level;

    private Long missionId;

    private Long currentStatus;

    private LocalDateTime achievedAt;

    private Boolean isCompensated;

    public static MashongMissionTeamLog of(
        Platform platform,
        MashongMissionLevel mashongMissionLevel
    ) {
        return new MashongMissionTeamLog(platform, mashongMissionLevel.getId(), null, mashongMissionLevel.getLevel(), mashongMissionLevel.getMashongMission().getId(), 0L, null, false);
    }

    public static MashongMissionTeamLog of(
        Platform platform,
        MashongMissionLevel mashongMissionLevel,
        String baseDate
    ) {
        return new MashongMissionTeamLog(platform, mashongMissionLevel.getId(), baseDate, mashongMissionLevel.getLevel(), mashongMissionLevel.getMashongMission().getId(), 0L, null, false);
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

    private MashongMissionTeamLog(
        Platform platform,
        Long missionLevelId,
        String baseDate,
        Long level,
        Long missionId,
        Long currentStatus,
        LocalDateTime achievedAt,
        Boolean isCompensated
    ) {
        this.platform = platform;
        this.missionLevelId = missionLevelId;
        this.baseDate = baseDate;
        this.level = level;
        this.missionId = missionId;
        this.currentStatus = currentStatus;
        this.achievedAt = achievedAt;
        this.isCompensated = isCompensated;
    }
}
