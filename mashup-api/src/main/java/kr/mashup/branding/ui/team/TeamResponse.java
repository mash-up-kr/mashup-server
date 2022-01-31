package kr.mashup.branding.ui.team;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class TeamResponse {
    private final Long teamId;
    private final String name;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
}
