package kr.mashup.branding.service.popup;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Service;

import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.popup.MemberPopup;
import kr.mashup.branding.domain.storage.Storage;
import kr.mashup.branding.repository.memberpopup.MemberPopupRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberPopupService {

	private final MemberPopupRepository memberPopupRepository;

	public MemberPopup findOrSaveMemberPopupByMemberAndStorage(Member member, Storage storage) {
		return memberPopupRepository.findByMemberAndStorage(member, storage)
			.orElseGet(() -> memberPopupRepository.save(MemberPopup.of(true, LocalDate.now(), member, storage)));
	}

	public Boolean isPossibleMemberPopup(Member member, Storage storage) {
		Optional<MemberPopup> memberPopup = memberPopupRepository.findByMemberAndStorage(member, storage);
		// 팝업 또는 페이지에 접근하지 않은 경우
		if (memberPopup.isEmpty()) {
			return true;
		}
		// 페이지에 방문하지 않은 경우(팝업 상태 활성화) && 마지막으로 본 일자가 현재 보다 과거인 경우
		return memberPopup.get().getIsEnabled() && memberPopup.get().getLastViewedAt().isBefore(LocalDate.now());
	}
}
