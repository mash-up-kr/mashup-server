package kr.mashup.branding.service.mashong.dto;

import kr.mashup.branding.domain.mashong.MashongMissionLevel;
import kr.mashup.branding.domain.mashong.MashongMissionLog;
import lombok.Getter;

@Getter
public class MissionStatus {
    private Long level;
    private Long goal;
    private String title;
    private Long compensation;
    private Long currentStatus;
    private Boolean isCompensated;

    private MissionStatus(
        Long level,
        Long goal,
        String title,
        Long compensation,
        Long currentStatus,
        Boolean isCompensated
    ) {
        this.level = level;
        this.goal = goal;
        this.title = title;
        this.compensation = compensation;
        this.currentStatus = currentStatus;
        this.isCompensated = isCompensated;
    }

    public static MissionStatus of(
        MashongMissionLevel mashongMissionLevel,
        MashongMissionLog mashongMissionLog
    ) {
        return new MissionStatus(
            mashongMissionLevel.getLevel(),
            mashongMissionLevel.getMissionGoalValue(),
            mashongMissionLevel.getTitle(),
            mashongMissionLevel.getCompensationValue(),
            mashongMissionLog.getCurrentStatus(),
            mashongMissionLog.getIsCompensated()
        );
    }
}
