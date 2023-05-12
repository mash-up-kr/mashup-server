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

	public DanggnNotificationMemberRecord findMemberRecordOrSave(MemberGeneration memberGeneration) {
		return danggnNotificationMemberRecordRepository.findByMemberGeneration(memberGeneration)
			.orElseGet(() -> danggnNotificationMemberRecordRepository.save(DanggnNotificationMemberRecord.of(memberGeneration, 0L)));
	}

	public DanggnNotificationPlatformRecord findPlatformRecordOrSave(Generation generation, Platform platform) {
		return danggnNotificationPlatformRecordRepository.findByPlatformAndGeneration(platform, generation)
			.orElseGet(() -> danggnNotificationPlatformRecordRepository.save(DanggnNotificationPlatformRecord.of(generation, platform, 0L)));
	}
}
