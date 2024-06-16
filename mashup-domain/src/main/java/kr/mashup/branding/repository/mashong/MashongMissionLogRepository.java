package kr.mashup.branding.repository.mashong;

import kr.mashup.branding.domain.mashong.MashongMissionLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MashongMissionLogRepository extends JpaRepository<MashongMissionLog, Long> {
    List<MashongMissionLog> findAllByMemberGenerationIdAndMissionId(Long memberGenerationId, Long missionId);

    List<MashongMissionLog> findAllByMemberGenerationIdAndMissionIdAndBaseDate(Long memberGenerationId, Long missionId, String baseDate);

    Optional<MashongMissionLog> findByMissionLevelIdAndMemberGenerationId(Long missionLevelId, Long memberGenerationId);

    Optional<MashongMissionLog> findByMissionLevelIdAndMemberGenerationIdAndBaseDate(Long missionLevelId, Long memberGenerationId, String baseDate);
}
