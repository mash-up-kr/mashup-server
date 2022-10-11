package kr.mashup.branding.ui.team;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import kr.mashup.branding.facade.team.TeamFacadeService;
import kr.mashup.branding.ui.ApiResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/teams")
@RequiredArgsConstructor
public class TeamController {
    private final TeamFacadeService teamFacadeService;

    @ApiOperation("팀 목록 조회")
    @GetMapping
    public ApiResponse<List<TeamResponse>> getTeams() {

        List<TeamResponse> responses = teamFacadeService.getTeams();


        return ApiResponse.success(responses);
    }
}
