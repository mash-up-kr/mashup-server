package kr.mashup.branding.facade.team;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.mashup.branding.domain.team.CreateTeamVo;
import kr.mashup.branding.domain.team.Team;
import kr.mashup.branding.service.team.TeamService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TeamFacadeServiceImpl implements TeamFacadeService {
    private final TeamService teamService;

    @Override
    public Team create(Long adminMemberId, CreateTeamVo createTeamVo) {
        return teamService.create(createTeamVo);
    }

    @Override
    public List<Team> getTeams(Long adminMemberId) {
        return teamService.findAllTeams();
    }
}
