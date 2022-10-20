package kr.mashup.branding.facade.team;

import java.util.List;
import java.util.stream.Collectors;

import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.service.generation.GenerationService;
import kr.mashup.branding.ui.team.TeamResponse;
import org.springframework.stereotype.Service;

import kr.mashup.branding.service.team.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TeamFacadeService {
    private final GenerationService generationService;
    private final TeamService teamService;

    @Transactional(readOnly = true)
    public List<TeamResponse> getTeams(Integer generationNumber) {
        final Generation generation = generationService.getByNumberOrThrow(generationNumber);
        return teamService.findAllTeamsByGeneration(generation)
            .stream()
            .map(TeamResponse::from)
            .collect(Collectors.toList());
    }
}
