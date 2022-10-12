package kr.mashup.branding.facade.team;

import kr.mashup.branding.domain.team.CreateTeamVo;
import kr.mashup.branding.domain.team.Team;
import kr.mashup.branding.service.team.TeamService;
import kr.mashup.branding.ui.team.vo.TeamResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminTeamFacadeService {
    private final TeamService teamService;

    public TeamResponse create(Long adminMemberId, CreateTeamVo createTeamVo) {
        final Team team = teamService.create(createTeamVo);
        return TeamResponse.from(team);
    }

    @Transactional(readOnly = true)
    public List<TeamResponse> getTeams(Long adminMemberId) {
        return teamService.findAllTeams()
            .stream()
            .map(TeamResponse::from)
            .collect(Collectors.toList());
    }
}
