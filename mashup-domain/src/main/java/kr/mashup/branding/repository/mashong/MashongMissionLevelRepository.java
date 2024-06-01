package kr.mashup.branding.repository.mashong;

import kr.mashup.branding.domain.mashong.MashongMissionLevel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MashongMissionLevelRepository extends JpaRepository<MashongMissionLevel, Long> {
    List<MashongMissionLevel> findAllByMissionId(Long missionId);
}
