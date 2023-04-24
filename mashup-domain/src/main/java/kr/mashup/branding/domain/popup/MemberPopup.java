package kr.mashup.branding.domain.popup;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import kr.mashup.branding.domain.BaseEntity;
import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.storage.Storage;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberPopup extends BaseEntity {

	private Boolean hasVisited;		// 페이지 접근 여부

	private LocalDate lastViewedAt;	// 팝업 마지막으로 본 날짜

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "member_id")
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "storage_id")
	private Storage storage;

	public static MemberPopup of(
		Boolean isVisible,
		LocalDate lastViewedAt,
		Member member,
		Storage storage
	) {
		return new MemberPopup(isVisible, lastViewedAt, member, storage);
	}

	private MemberPopup(
		Boolean hasVisited,
		LocalDate lastViewedAt,
		Member member,
		Storage storage
	) {
		this.hasVisited = hasVisited;
		this.lastViewedAt = lastViewedAt;
		this.member = member;
		this.storage = storage;
	}

	public void updateHasVisited(Boolean isVisible) {
		this.hasVisited = isVisible;
	}

	public void updateLastViewedAt(LocalDate at) {
		this.lastViewedAt = at;
	}
}
