package kr.mashup.branding.service.danggn;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum DanggnNotificationSentUnit {

	MEMBER_RECORD(10_000L),
	PLATFORM_RECORD(100_000L),
	;

	private final Long unit;

	public boolean isNotThreshold(Long previous, Long latest) {
		return (previous / this.unit) >= (latest / this.unit);
	}

	public Long calculateUnit(Long score) {
		return score / this.unit * this.unit;
	}
}