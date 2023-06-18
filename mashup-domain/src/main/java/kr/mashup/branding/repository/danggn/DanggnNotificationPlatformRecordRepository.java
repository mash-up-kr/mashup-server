package kr.mashup.branding.repository.danggn;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.mashup.branding.domain.danggn.DanggnNotificationPlatformRecord;
import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.member.Platform;

public interface DanggnNotificationPlatformRecordRepository extends JpaRepository<DanggnNotificationPlatformRecord, Long> {

	Optional<DanggnNotificationPlatformRecord> findByPlatformAndGenerationAndDanggnRankingRoundId(Platform platform, Generation generation, Long danggnRankingRoundId);
}
