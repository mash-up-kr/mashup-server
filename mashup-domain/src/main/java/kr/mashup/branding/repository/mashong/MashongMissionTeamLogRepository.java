package kr.mashup.branding.repository.mashong;

import kr.mashup.branding.domain.mashong.MashongMissionTeamLog;
import kr.mashup.branding.domain.member.Platform;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MashongMissionTeamLogRepository extends JpaRepository<MashongMissionTeamLog, Long> {
    List<MashongMissionTeamLog> findAllByPlatformAndGenerationIdAndMissionId(Platform platform, Long generationId, Long missionId);

    List<MashongMissionTeamLog> findAllByPlatformAndGenerationIdAndMissionIdAndBaseDate(Platform platform, Long generationId, Long missionId, String baseDate);

    Optional<MashongMissionTeamLog> findByMissionLevelIdAndPlatformAndGenerationId(Long missionLevelId, Platform platform, Long generationId);

    Optional<MashongMissionTeamLog> findByMissionLevelIdAndPlatformAndGenerationIdAndBaseDate(Long missionLevelId, Platform platform, Long generationId, String baseDate);
}
