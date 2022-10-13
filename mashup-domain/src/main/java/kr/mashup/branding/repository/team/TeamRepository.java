package kr.mashup.branding.repository.team;

import kr.mashup.branding.domain.team.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
/**
 * Team 연관관계
 * 없음
 */