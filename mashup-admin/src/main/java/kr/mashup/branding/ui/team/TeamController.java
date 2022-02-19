package kr.mashup.branding.ui.team;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.mashup.branding.domain.team.CreateTeamVo;
import kr.mashup.branding.domain.team.Team;
import kr.mashup.branding.facade.team.TeamFacadeService;
import kr.mashup.branding.ui.ApiResponse;
import lombok.RequiredArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/v1/teams")
@RequiredArgsConstructor
public class TeamController {
    private final TeamFacadeService teamFacadeService;
    private final TeamAssembler teamAssembler;

    @GetMapping
    public ApiResponse<List<TeamResponse>> getTeams(
        @ApiIgnore @ModelAttribute("adminMemberId") Long adminMemberId
    ) {
        return ApiResponse.success(
            teamFacadeService.getTeams(adminMemberId)
                .stream()
                .map(teamAssembler::toTeamResponse)
                .collect(Collectors.toList())
        );
    }

    @PostMapping
    public ApiResponse<TeamResponse> create(
        @ApiIgnore @ModelAttribute("adminMemberId") Long adminMemberId,
        @RequestBody CreateTeamRequest createTeamRequest
    ) {
        CreateTeamVo createTeamVo = teamAssembler.toCreateTeamVo(createTeamRequest);
        Team team = teamFacadeService.create(adminMemberId, createTeamVo);
        return ApiResponse.success(
            teamAssembler.toTeamResponse(team)
        );
    }
}
