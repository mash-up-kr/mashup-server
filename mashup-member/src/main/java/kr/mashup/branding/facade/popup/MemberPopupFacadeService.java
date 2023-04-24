package kr.mashup.branding.facade.popup;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.mashup.branding.domain.member.MemberGeneration;
import kr.mashup.branding.domain.member.exception.InactiveGenerationException;
import kr.mashup.branding.domain.popup.MemberPopup;
import kr.mashup.branding.domain.storage.Storage;
import kr.mashup.branding.service.member.MemberService;
import kr.mashup.branding.service.popup.MemberPopupService;
import kr.mashup.branding.service.storage.StorageService;
import kr.mashup.branding.util.DateUtil;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberPopupFacadeService {

	private final MemberPopupService memberPopupService;
	private final MemberService memberService;
	private final StorageService storageService;

	public List<String> getPossiblePopupKeys(
		Long memberGenerationId
	) {

		List<String> possiblePopupKeys = new ArrayList<>();
		MemberGeneration memberGeneration = memberService.findByMemberGenerationId(memberGenerationId);

		// 멤버의 기수가 활동 중인 기수가 아닌 경우
		if (!memberService.isActiveGeneration(memberGeneration)) {
			return possiblePopupKeys;
		}

		// 현재 운영하는 팝업의 Storage key 와 운영기간(새로운 팝업 추가시 변경이 필요한 값)
		String popupKey = "danggnPopup";
		Storage storage = storageService.findByKey(popupKey);
		LocalDate popupStartedAt = LocalDate.of(2023, 4, 1);
		LocalDate popupEndedAt = LocalDate.of(2023, 5, 15);

		if (DateUtil.isInTime(popupStartedAt, popupEndedAt, LocalDate.now())
			&& memberPopupService.isPossibleMemberPopup(memberGeneration.getMember(), storage)) {
			possiblePopupKeys.add(popupKey);
		}
		return possiblePopupKeys;
	}

	@Transactional
	public void updateHasVisited(
		Long memberGenerationId,
		String popupType
	) {

		MemberPopup memberPopup = getUpdatableByMemberGenerationAndStorage(memberGenerationId, popupType);
		memberPopup.updateHasVisited(true);
	}

	@Transactional
	public void updateLastViewedAt(
		Long memberGenerationId,
		String popupType
	) {

		MemberPopup memberPopup = getUpdatableByMemberGenerationAndStorage(memberGenerationId, popupType);
		memberPopup.updateLastViewedAt(LocalDate.now());
	}

	private MemberPopup getUpdatableByMemberGenerationAndStorage(
		Long memberGenerationId,
		String popupType
	) {

		Storage storage = storageService.findByKey(popupType);

		MemberGeneration memberGeneration = memberService.findByMemberGenerationId(memberGenerationId);
		if (!memberService.isActiveGeneration(memberGeneration)) {
			throw new InactiveGenerationException();
		}

		return memberPopupService.findOrSaveMemberPopupByMemberAndStorage(memberGeneration.getMember(), storage);
	}
}
