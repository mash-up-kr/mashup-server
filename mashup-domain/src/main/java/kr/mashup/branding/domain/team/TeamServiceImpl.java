package kr.mashup.branding.domain.team;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;

    @Override
    @Transactional
    public Team createTeam(CreateTeamVo createTeamVo) {
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
