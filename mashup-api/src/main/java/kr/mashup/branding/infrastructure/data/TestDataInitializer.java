package kr.mashup.branding.infrastructure.data;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import kr.mashup.branding.domain.team.CreateTeamVo;
import kr.mashup.branding.domain.team.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Profile({"local", "develop"})
@Slf4j
@Component
@RequiredArgsConstructor
public class TestDataInitializer {
    private final TeamService teamService;

    @EventListener(ApplicationReadyEvent.class)
    public void initialize() {
        createTeams();
    }

    private void createTeams() {
        teamService.createTeam(CreateTeamVo.of("Design"));
        teamService.createTeam(CreateTeamVo.of("Web"));
        teamService.createTeam(CreateTeamVo.of("Android"));
        teamService.createTeam(CreateTeamVo.of("iOS"));
        teamService.createTeam(CreateTeamVo.of("Node"));
        teamService.createTeam(CreateTeamVo.of("Spring"));
        log.info("6 Teams are created. teams: {}", teamService.findAllTeams());
    }
}
