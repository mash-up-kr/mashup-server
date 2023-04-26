package kr.mashup.branding.facade.popup;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.mashup.branding.domain.member.MemberGeneration;
import kr.mashup.branding.domain.member.exception.InactiveGenerationException;
import kr.mashup.branding.domain.popup.MemberPopup;
import kr.mashup.branding.domain.popup.PopupType;
import kr.mashup.branding.service.member.MemberService;
import kr.mashup.branding.service.popup.MemberPopupService;
import kr.mashup.branding.service.storage.StorageService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberPopupFacadeService {

	private final MemberPopupService memberPopupService;
	private final MemberService memberService;
	private final StorageService storageService;

	public List<PopupType> getEnabledPopupTypes(
		Long memberGenerationId
	) {

		List<PopupType> enabledMemberPopupTypes = new ArrayList<>();
		MemberGeneration memberGeneration = memberService.findByMemberGenerationId(memberGenerationId);

		// 멤버의 기수가 활동 중인 기수가 아닌 경우
		if (!memberService.isActiveGeneration(memberGeneration)) {
			return enabledMemberPopupTypes;
		}

		// 운영 가능한 팝업 중에 멤버가 볼 수 있는 팝업 조회
		List<PopupType> activePopupTypes = PopupType.findActives(LocalDate.now());
		activePopupTypes.stream()
			.filter(popupType -> memberPopupService.isEnabledMemberPopup(memberGeneration.getMember(), popupType))
			.forEach(enabledMemberPopupTypes::add);

		return enabledMemberPopupTypes;
	}

	@Transactional
	public void updateDisabled(
		Long memberGenerationId,
		PopupType popupType
	) {

		MemberPopup memberPopup = getUpdatableByMemberGenerationAndType(memberGenerationId, popupType);
		memberPopup.updateIsEnabled(false);
	}

	@Transactional
	public void updateLastViewedAt(
		Long memberGenerationId,
		PopupType popupType
	) {

		MemberPopup memberPopup = getUpdatableByMemberGenerationAndType(memberGenerationId, popupType);
		memberPopup.updateLastViewedAt(LocalDateTime.now());
	}

	private MemberPopup getUpdatableByMemberGenerationAndType(
		Long memberGenerationId,
		PopupType popupType
	) {

		MemberGeneration memberGeneration = memberService.findByMemberGenerationId(memberGenerationId);
		if (!memberService.isActiveGeneration(memberGeneration)) {
			throw new InactiveGenerationException();
		}

		return memberPopupService.findOrSaveMemberPopupByMemberAndType(memberGeneration.getMember(), popupType);
	}
}
