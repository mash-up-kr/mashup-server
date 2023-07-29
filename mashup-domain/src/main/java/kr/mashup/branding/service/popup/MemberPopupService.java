package kr.mashup.branding.service.popup;

import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.popup.MemberPopup;
import kr.mashup.branding.domain.popup.PopupType;
import kr.mashup.branding.repository.memberpopup.MemberPopupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberPopupService {

	private final MemberPopupRepository memberPopupRepository;

	public MemberPopup findOrSaveMemberPopupByMemberAndType(Member member, PopupType popupType) {
		return memberPopupRepository.findByMemberAndPopupType(member, popupType)
			.orElseGet(() -> memberPopupRepository.save(MemberPopup.of(true, LocalDateTime.now(), member, popupType)));
	}

	public Boolean isEnabledMemberPopup(Member member, PopupType popupType) {
		Optional<MemberPopup> memberPopup = memberPopupRepository.findByMemberAndPopupType(member, popupType);
		// 팝업 또는 페이지에 접근하지 않은 경우
		if (memberPopup.isEmpty()) {
			return true;
		}
		// 페이지에 방문하지 않은 경우(팝업 상태 활성화) && 마지막으로 본 일자가 현재 보다 과거인 경우
		return memberPopup.get().getIsEnabled() && memberPopup.get().getLastViewedAt().toLocalDate().isBefore(LocalDate.now());
	}

	public void save(Member member, PopupType popupType) {
		memberPopupRepository.save(MemberPopup.of(true, LocalDateTime.now().minusDays(1), member, popupType));
	}
}
