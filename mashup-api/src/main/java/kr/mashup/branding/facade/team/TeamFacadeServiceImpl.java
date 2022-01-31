package kr.mashup.branding.facade.team;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.mashup.branding.domain.team.Team;
import kr.mashup.branding.domain.team.TeamService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TeamFacadeServiceImpl implements TeamFacadeService {
    private final TeamService teamService;

    @Override
    public List<Team> getTeams() {
        return teamService.findAllTeams();
    }
}
