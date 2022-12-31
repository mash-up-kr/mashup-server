package kr.mashup.branding.ui.team;

import java.util.List;

import kr.mashup.branding.ui.team.vo.CreateTeamRequest;
import kr.mashup.branding.ui.team.vo.TeamResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.mashup.branding.facade.team.AdminTeamFacadeService;
import kr.mashup.branding.ui.ApiResponse;
import lombok.RequiredArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/v1/teams")
@RequiredArgsConstructor
public class AdminTeamController {
    private final AdminTeamFacadeService adminTeamFacadeService;

    @GetMapping
    public ApiResponse<List<TeamResponse>> getTeams(
        @ApiIgnore @ModelAttribute("adminMemberId") Long adminMemberId,
        @RequestParam(defaultValue = "13", required = false) Integer generationNumber
    ) {

        final List<TeamResponse> responses
            = adminTeamFacadeService.getTeams(adminMemberId, generationNumber);

        return ApiResponse.success(responses);
    }

    @PostMapping
    public ApiResponse<TeamResponse> create(
        @ApiIgnore @ModelAttribute("adminMemberId") Long adminMemberId,
        @RequestBody CreateTeamRequest createTeamRequest
    ) {

        final TeamResponse response
            =  adminTeamFacadeService.create(adminMemberId, createTeamRequest);

        return ApiResponse.success(response);
    }
}
