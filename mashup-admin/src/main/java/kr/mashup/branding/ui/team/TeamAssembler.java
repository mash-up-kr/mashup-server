package kr.mashup.branding.ui.team;

import org.springframework.stereotype.Component;

import kr.mashup.branding.domain.team.CreateTeamVo;
import kr.mashup.branding.domain.team.Team;

@Component
public class TeamAssembler {
    CreateTeamVo toCreateTeamVo(CreateTeamRequest createTeamRequest) {
        return CreateTeamVo.of(
            createTeamRequest.getName()
        );
    }

    TeamResponse toTeamResponse(Team team) {
        return new TeamResponse(
            team.getTeamId(),
            team.getName()
        );
    }
}
