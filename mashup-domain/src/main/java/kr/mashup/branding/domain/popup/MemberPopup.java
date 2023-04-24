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

	private Boolean isEnabled;			// 팝업 활성화 여부

	private LocalDate lastViewedAt;		// 팝업 마지막으로 본 날짜

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "member_id")
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "storage_id")
	private Storage storage;

	public static MemberPopup of(
		Boolean isEnabled,
		LocalDate lastViewedAt,
		Member member,
		Storage storage
	) {
		return new MemberPopup(isEnabled, lastViewedAt, member, storage);
	}

	private MemberPopup(
		Boolean isEnabled,
		LocalDate lastViewedAt,
		Member member,
		Storage storage
	) {
		this.isEnabled = isEnabled;
		this.lastViewedAt = lastViewedAt;
		this.member = member;
		this.storage = storage;
	}

	public void updateIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public void updateLastViewedAt(LocalDate at) {
		this.lastViewedAt = at;
	}
}
