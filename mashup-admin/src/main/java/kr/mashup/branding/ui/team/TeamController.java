package kr.mashup.branding.ui.team;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.mashup.branding.domain.team.CreateTeamVo;
import kr.mashup.branding.domain.team.Team;
import kr.mashup.branding.facade.team.TeamFacadeService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/teams")
@RequiredArgsConstructor
public class TeamController {
    private final TeamFacadeService teamFacadeService;
    private final TeamAssembler teamAssembler;

    @GetMapping
    public List<TeamResponse> getTeams() {
        Long adminMemberId = 0L;
        return teamFacadeService.getTeams(adminMemberId)
            .stream()
            .map(teamAssembler::toTeamResponse)
            .collect(Collectors.toList());
    }

    @PostMapping
    public TeamResponse create(
        @RequestBody CreateTeamRequest createTeamRequest
    ) {
        Long adminMemberId = 0L;
        CreateTeamVo createTeamVo = teamAssembler.toCreateTeamVo(createTeamRequest);
        Team team = teamFacadeService.create(adminMemberId, createTeamVo);
        return teamAssembler.toTeamResponse(team);
    }
}
