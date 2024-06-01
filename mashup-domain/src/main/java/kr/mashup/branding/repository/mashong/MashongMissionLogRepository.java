package kr.mashup.branding.repository.mashong;

import kr.mashup.branding.domain.mashong.MashongMissionLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MashongMissionLogRepository extends JpaRepository<MashongMissionLog, Long> {
    List<MashongMissionLog> findAllByMemberGenerationIdAndMissionId(Long memberGenerationId, Long missionId);
}
