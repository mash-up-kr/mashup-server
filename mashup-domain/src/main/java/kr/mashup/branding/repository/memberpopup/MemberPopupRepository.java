package kr.mashup.branding.repository.memberpopup;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.popup.MemberPopup;
import kr.mashup.branding.domain.storage.Storage;

public interface MemberPopupRepository extends JpaRepository<MemberPopup, Long> {

	Optional<MemberPopup> findByMemberAndStorage(Member member, Storage storage);
}
