package kr.mashup.branding.repository.mashong;

import kr.mashup.branding.domain.mashong.MashongMission;
import kr.mashup.branding.domain.mashong.MissionStrategyType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MashongMissionRepository extends JpaRepository<MashongMission, Long> {
    Optional<MashongMission> findByMissionStrategyType(MissionStrategyType missionStrategyType);
}
