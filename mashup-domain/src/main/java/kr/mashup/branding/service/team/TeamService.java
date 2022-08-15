package kr.mashup.branding.service.team;

import kr.mashup.branding.domain.team.CreateTeamVo;
import kr.mashup.branding.domain.team.Team;

import java.util.List;

public interface TeamService {
    Team create(CreateTeamVo createTeamVo);

    Team getTeam(Long teamId);

    List<Team> findAllTeams();
}
