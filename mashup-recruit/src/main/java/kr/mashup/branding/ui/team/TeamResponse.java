package kr.mashup.branding.ui.team;

import kr.mashup.branding.domain.team.Team;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TeamResponse {
    private Long teamId;
    private String name;

    public static TeamResponse from(Team team){
        return new TeamResponse(team.getTeamId(), team.getName());
    }
}
