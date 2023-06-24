package kr.mashup.branding.service.danggn;

import org.springframework.stereotype.Service;

import kr.mashup.branding.domain.danggn.DanggnNotificationMemberRecord;
import kr.mashup.branding.domain.danggn.DanggnNotificationPlatformRecord;
import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.member.MemberGeneration;
import kr.mashup.branding.domain.member.Platform;
import kr.mashup.branding.repository.danggn.DanggnNotificationMemberRecordRepository;
import kr.mashup.branding.repository.danggn.DanggnNotificationPlatformRecordRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DanggnNotificationRecordService {

	private final DanggnNotificationMemberRecordRepository danggnNotificationMemberRecordRepository;

	private final DanggnNotificationPlatformRecordRepository danggnNotificationPlatformRecordRepository;

	public DanggnNotificationMemberRecord findMemberRecordOrSave(MemberGeneration memberGeneration, Long danggnRankingRoundId) {
		return danggnNotificationMemberRecordRepository.findByMemberGenerationAndDanggnRankingRoundId(memberGeneration, danggnRankingRoundId)
			.orElseGet(() -> danggnNotificationMemberRecordRepository.save(DanggnNotificationMemberRecord.of(memberGeneration, 0L, danggnRankingRoundId)));
	}

	public DanggnNotificationPlatformRecord findPlatformRecordOrSave(Generation generation, Platform platform, Long danggnRankingRoundId) {
		return danggnNotificationPlatformRecordRepository.findByPlatformAndGenerationAndDanggnRankingRoundId(platform, generation, danggnRankingRoundId)
			.orElseGet(() -> danggnNotificationPlatformRecordRepository.save(DanggnNotificationPlatformRecord.of(generation, platform, 0L, danggnRankingRoundId)));
	}
}
