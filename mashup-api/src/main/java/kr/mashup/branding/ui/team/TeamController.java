package kr.mashup.branding.ui.team;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/teams")
public class TeamController {
    @ApiOperation("팀 목록 조회")
    @GetMapping
    public List<TeamResponse> getTeams() {
        return Collections.emptyList();
    }
}
