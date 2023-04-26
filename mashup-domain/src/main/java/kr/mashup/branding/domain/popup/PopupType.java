package kr.mashup.branding.domain.popup;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import kr.mashup.branding.util.DateUtil;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum PopupType {

	DANGGN(LocalDate.of(2023, 4, 1), LocalDate.of(2023, 5, 15)), // 운영기간은 수정 필요
	;

	private final LocalDate startedAt;
	private final LocalDate endedAt;

	public static List<PopupType> findActives(LocalDate at) {
		return Stream.of(PopupType.values())
			.filter(popupType -> DateUtil.isInTime(popupType.startedAt, popupType.endedAt, at))
			.collect(Collectors.toList());
	}
}