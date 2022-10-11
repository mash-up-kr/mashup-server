package kr.mashup.branding.facade.team;

import java.util.List;
import java.util.stream.Collectors;

import kr.mashup.branding.ui.team.TeamResponse;
import org.springframework.stereotype.Service;

import kr.mashup.branding.service.team.TeamService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TeamFacadeService {
    private final TeamService teamService;

    public List<TeamResponse> getTeams() {
        return teamService.findAllTeams()
            .stream()
            .map(TeamResponse::from)
            .collect(Collectors.toList());
    }
}
