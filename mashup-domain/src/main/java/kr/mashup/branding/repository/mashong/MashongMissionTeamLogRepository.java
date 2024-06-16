package kr.mashup.branding.repository.mashong;

import kr.mashup.branding.domain.mashong.MashongMissionTeamLog;
import kr.mashup.branding.domain.member.Platform;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MashongMissionTeamLogRepository extends JpaRepository<MashongMissionTeamLog, Long> {
    List<MashongMissionTeamLog> findAllByPlatformAndMissionId(Platform platform, Long missionId);

    List<MashongMissionTeamLog> findAllByPlatformAndMissionIdAndBaseDate(Platform platform, Long missionId, String baseDate);

    Optional<MashongMissionTeamLog> findByMissionLevelIdAndPlatform(Long missionLevelId, Platform platform);

    Optional<MashongMissionTeamLog> findByMissionLevelIdAndPlatformAndBaseDate(Long missionLevelId, Platform platform, String baseDate);
}
