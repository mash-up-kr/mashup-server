package kr.mashup.branding.service.mashong.dto;

import lombok.Getter;

@Getter
public class MissionStatus {
    private Boolean isAchieved;
    private Long currentStatus;
    private String title;
    private String description;

    private MissionStatus(
        Boolean isAchieved,
        Long currentStatus,
        String title,
        String description
    ) {
        this.isAchieved = isAchieved;
        this.currentStatus = currentStatus;
        this.title = title;
        this.description = description;
    }

    public static MissionStatus of(
        Boolean isAchieved,
        Long currentStatus,
        String title,
        String description
    ) {
        return new MissionStatus(isAchieved, currentStatus, title, description);
    }
}
