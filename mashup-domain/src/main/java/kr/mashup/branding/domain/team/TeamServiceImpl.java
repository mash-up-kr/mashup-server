package kr.mashup.branding.domain.team;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;

    @Override
    @Transactional
    public Team create(CreateTeamVo createTeamVo) {
        return teamRepository.save(Team.of(createTeamVo));
    }

    @Override
    public Team getTeam(Long teamId) {
        return teamRepository.findById(teamId).orElseThrow(TeamNotFoundException::new);
    }

    @Override
    public List<Team> findAllTeams() {
        return teamRepository.findAll();
    }
}
