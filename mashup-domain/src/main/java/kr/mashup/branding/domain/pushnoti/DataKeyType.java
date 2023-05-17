package kr.mashup.branding.domain.pushnoti;

import lombok.Getter;

@Getter
public enum DataKeyType {
	LINK("link");

	private final String key;

	DataKeyType(String key) {
		this.key = key;
	}
}
