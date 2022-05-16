package kr.mashup.branding.ui.team;

import org.springframework.stereotype.Component;

import kr.mashup.branding.domain.team.Team;

@Component
public class TeamAssembler {
    public TeamResponse toTeamResponse(Team team) {
        return new TeamResponse(
            team.getTeamId(),
            team.getName()
        );
    }
}
