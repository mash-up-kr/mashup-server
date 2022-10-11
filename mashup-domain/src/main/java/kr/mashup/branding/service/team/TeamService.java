package kr.mashup.branding.service.team;

import java.util.List;
import java.util.stream.Collectors;

import kr.mashup.branding.domain.team.CreateTeamVo;
import kr.mashup.branding.domain.team.Team;
import kr.mashup.branding.domain.team.TeamNotFoundException;
import kr.mashup.branding.repository.team.TeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Service
@Transactional
@Validated
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;

    public Team create(CreateTeamVo createTeamVo) {
        Team team = teamRepository.save(Team.of(createTeamVo));
        return team;
    }

    @Transactional(readOnly = true)
    public Team getTeam(Long teamId) {
        Team team = teamRepository.findById(teamId).orElseThrow(TeamNotFoundException::new);
        return team;
    }

    @Transactional(readOnly = true)
    public List<Team> findAllTeams() {

        return teamRepository.findAll();
    }
}
