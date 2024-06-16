package kr.mashup.branding.service.mashong.dto;

import kr.mashup.branding.domain.mashong.*;
import lombok.Getter;

@Getter
public class MissionStatus {
    private Long missionLevelId;
    private MissionType missionType;
    private MissionRepeatType missionRepeatType;
    private Long level;
    private Long goal;
    private String title;
    private Long compensation;
    private Long currentStatus;
    private Boolean isCompensated;

    private MissionStatus(
        Long missionLevelId,
        MissionType missionType,
        MissionRepeatType missionRepeatType,
        Long level,
        Long goal,
        String title,
        Long compensation,
        Long currentStatus,
        Boolean isCompensated
    ) {
        this.missionLevelId = missionLevelId;
        this.missionType = missionType;
        this.missionRepeatType = missionRepeatType;
        this.level = level;
        this.goal = goal;
        this.title = title;
        this.compensation = compensation;
        this.currentStatus = currentStatus;
        this.isCompensated = isCompensated;
    }

    public static MissionStatus of(
        MashongMission mashongMission,
        MashongMissionLevel mashongMissionLevel,
        MashongMissionLog mashongMissionLog
    ) {
        return new MissionStatus(
            mashongMissionLevel.getId(),
            mashongMission.getMissionType(),
            mashongMission.getMissionRepeatType(),
            mashongMissionLevel.getLevel(),
            mashongMissionLevel.getMissionGoalValue(),
            mashongMissionLevel.getTitle(),
            mashongMissionLevel.getCompensationValue(),
            mashongMissionLog.getCurrentStatus(),
            mashongMissionLog.getIsCompensated()
        );
    }

    public static MissionStatus of(
        MashongMission mashongMission,
        MashongMissionLevel mashongMissionLevel,
        MashongMissionTeamLog mashongMissionLog
    ) {
        return new MissionStatus(
            mashongMissionLevel.getId(),
            mashongMission.getMissionType(),
            mashongMission.getMissionRepeatType(),
            mashongMissionLevel.getLevel(),
            mashongMissionLevel.getMissionGoalValue(),
            mashongMissionLevel.getTitle(),
            mashongMissionLevel.getCompensationValue(),
            mashongMissionLog.getCurrentStatus(),
            mashongMissionLog.getIsCompensated()
        );
    }
}
