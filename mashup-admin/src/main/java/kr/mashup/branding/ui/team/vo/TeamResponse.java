package kr.mashup.branding.ui.team.vo;

import java.time.LocalDateTime;

import kr.mashup.branding.domain.team.Team;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TeamResponse {
    private Long teamId;
    private String name;
    private String createdBy;
    private LocalDateTime createdAt;
    private String updatedBy;
    private LocalDateTime updatedAt;

    static public TeamResponse from(Team team){
        return new TeamResponse(team.getTeamId(), team.getName(), team.getCreatedBy(), team.getCreatedAt(), team.getUpdatedBy(), team.getUpdatedAt());
    }
}
