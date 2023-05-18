package kr.mashup.branding.service.danggn;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum DanggnNotificationSentUnit {

	MEMBER_RECORD(50_000L),
	PLATFORM_RECORD(100_000L),
	;

	private final Long unit;

	public boolean isNotThreshold(Long previous, Long latest) {
		return (previous / this.unit) >= (latest / this.unit);
	}

	public Long calculateStage(Long latest) {
		return (latest / this.unit) * this.unit;
	}
}
