package kr.mashup.branding.repository.memberpopup;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.popup.MemberPopup;
import kr.mashup.branding.domain.popup.PopupType;

public interface MemberPopupRepository extends JpaRepository<MemberPopup, Long> {

	Optional<MemberPopup> findByMemberAndPopupType(Member member, PopupType popupType);

	void deleteByMember(Member member);
}
