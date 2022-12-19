package kr.mashup.branding.ui.team

import io.swagger.annotations.ApiOperation
import kr.mashup.branding.facade.team.TeamFacadeService
import kr.mashup.branding.ui.ApiResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/teams")
class TeamController(
    private val teamFacadeService: TeamFacadeService
) {

    @ApiOperation("팀 목록 조회")
    @GetMapping
    fun getTeams(
        @RequestParam(defaultValue = "13", required = false) generationNumber: Int?
    ): ApiResponse<List<TeamResponse>> {
        val responses = teamFacadeService.getTeams(generationNumber)
        return ApiResponse.success(responses)
    }
}