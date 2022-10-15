package kr.mashup.branding.service.team;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.team.CreateTeamVo;
import kr.mashup.branding.domain.team.Team;
import kr.mashup.branding.domain.team.TeamNotFoundException;
import kr.mashup.branding.repository.team.TeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;

    @Transactional
    public Team create(CreateTeamVo createTeamVo) {
        Team team = teamRepository.save(Team.of(createTeamVo));
        return team;
    }

    public Team getTeam(Long teamId) {
        Team team = teamRepository.findById(teamId).orElseThrow(TeamNotFoundException::new);
        return team;
    }

   public boolean isExistTeam(Long teamId){
       Optional<Team> team = teamRepository.findById(teamId);
       return team.isPresent();
   }


    @Transactional(readOnly = true)
    public List<Team> findAllTeamsByGeneration(Generation generation) {

        return teamRepository.findByGeneration(generation);
    }
}
