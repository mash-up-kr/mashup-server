package kr.mashup.branding.service.mashong.dto;

import kr.mashup.branding.domain.mashong.MashongMission;
import kr.mashup.branding.domain.mashong.MashongMissionLevel;
import kr.mashup.branding.domain.mashong.MashongMissionLog;
import kr.mashup.branding.domain.mashong.MissionType;
import lombok.Getter;

@Getter
public class MissionStatus {
    private Long missionLevelId;
    private MissionType missionType;
    private Long level;
    private Long goal;
    private String title;
    private Long compensation;
    private Long currentStatus;
    private Boolean isCompensated;

    private MissionStatus(
        Long missionLevelId,
        MissionType missionType,
        Long level,
        Long goal,
        String title,
        Long compensation,
        Long currentStatus,
        Boolean isCompensated
    ) {
        this.missionLevelId = missionLevelId;
        this.missionType = missionType;
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
            mashongMissionLevel.getLevel(),
            mashongMissionLevel.getMissionGoalValue(),
            mashongMissionLevel.getTitle(),
            mashongMissionLevel.getCompensationValue(),
            mashongMissionLog.getCurrentStatus(),
            mashongMissionLog.getIsCompensated()
        );
    }
}
