package kr.mashup.branding.domain.popup;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import kr.mashup.branding.domain.BaseEntity;
import kr.mashup.branding.domain.member.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberPopup extends BaseEntity {

	private Boolean isEnabled;			// 팝업 활성화 여부

	private LocalDateTime lastViewedAt;		// 팝업 마지막으로 본 날짜

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "member_id")
	private Member member;

	@Enumerated(EnumType.STRING)
	private PopupType popupType;

	public static MemberPopup of(
		Boolean isEnabled,
		LocalDateTime lastViewedAt,
		Member member,
		PopupType popupType
	) {
		return new MemberPopup(isEnabled, lastViewedAt, member, popupType);
	}

	private MemberPopup(
		Boolean isEnabled,
		LocalDateTime lastViewedAt,
		Member member,
		PopupType popupType
	) {
		this.isEnabled = isEnabled;
		this.lastViewedAt = lastViewedAt;
		this.member = member;
		this.popupType = popupType;
	}

	public void updateIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public void updateLastViewedAt(LocalDateTime at) {
		this.lastViewedAt = at;
	}
}
