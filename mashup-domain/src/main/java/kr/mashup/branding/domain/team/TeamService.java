package kr.mashup.branding.domain.team;

import java.util.List;

public interface TeamService {
    Team create(CreateTeamVo createTeamVo);

    Team getTeam(Long teamId);

    List<Team> findAllTeams();
}
