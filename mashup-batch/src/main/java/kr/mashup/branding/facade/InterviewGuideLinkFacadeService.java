package kr.mashup.branding.facade;

import java.util.List;
import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.team.Team;
import kr.mashup.branding.service.application.ApplicationService;
import kr.mashup.branding.service.generation.GenerationService;
import kr.mashup.branding.service.team.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InterviewGuideLinkFacadeService {

    private final ApplicationService applicationService;
    private final GenerationService generationService;
    private final TeamService teamService;

    public void setLink(
        String platformStr,
        Integer generationNum,
        String link
    ) {
        final Generation generation = generationService.getByNumberOrThrow(
            generationNum
        );
        final List<Team> teams =
            teamService.findAllTeamsByGeneration(generation);

        final Long selectedTeamId = getTeamId(teams, platformStr);

        applicationService.updateInterviewGuideLink(selectedTeamId, link);
    }

    private Long getTeamId(List<Team> teams, String platformStr) {
        if (platformStr.equals("ALL")) return null;

        return teams.stream()
            .filter(team -> {
                final String teamName = team.getName().toUpperCase();
                return teamName.equals(platformStr);
            })
            .map(Team::getTeamId)
            .findFirst()
            .orElseThrow();
    }
}
