package kr.mashup.branding.facade.team;

import java.util.List;

import kr.mashup.branding.domain.team.CreateTeamVo;
import kr.mashup.branding.domain.team.Team;

public interface TeamFacadeService {
    Team create(Long adminMemberId, CreateTeamVo createTeamVo);

    List<Team> getTeams(Long adminMemberId);
}
