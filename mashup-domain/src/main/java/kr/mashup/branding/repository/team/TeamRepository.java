package kr.mashup.branding.repository.team;

import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.team.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Long> {
    List<Team> findByGeneration(Generation generation);
}
/**
 * Team 연관관계
 * many to one : generation
 */