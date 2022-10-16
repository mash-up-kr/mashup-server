package kr.mashup.branding.facade.team;

import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.team.CreateTeamVo;
import kr.mashup.branding.domain.team.Team;
import kr.mashup.branding.service.generation.GenerationService;
import kr.mashup.branding.service.team.TeamService;
import kr.mashup.branding.ui.team.vo.CreateTeamRequest;
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
    private final GenerationService generationService;
    private final TeamService teamService;

    public TeamResponse create(Long adminMemberId, CreateTeamRequest createTeamRequest) {
        final Integer generationNumber = createTeamRequest.getGenerationNumber();
        final Generation generation = generationService.getByNumberOrThrow(generationNumber);

        final String teamName = createTeamRequest.getName();
        final Team team = teamService.create(CreateTeamVo.of(generation, teamName));

        return TeamResponse.from(team);
    }

    @Transactional(readOnly = true)
    public List<TeamResponse> getTeams(Long adminMemberId, Integer generationNumber) {

        final Generation generation = generationService.getByNumberOrThrow(generationNumber);
        return teamService.findAllTeamsByGeneration(generation)
            .stream()
            .map(TeamResponse::from)
            .collect(Collectors.toList());
    }
}
