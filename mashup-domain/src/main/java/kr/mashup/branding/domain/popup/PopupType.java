package kr.mashup.branding.domain.popup;

import kr.mashup.branding.util.DateUtil;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
public enum PopupType {

	DANGGN_REWARD(LocalDate.MIN, LocalDate.MAX),
	BIRTHDAY_CELEBRATION(LocalDate.MIN, LocalDate.MAX),
	;

	private final LocalDate startedDate;
	private final LocalDate endedDate;

	public static List<PopupType> findActives(LocalDate at) {
		return Stream.of(PopupType.values())
			.filter(popupType -> DateUtil.isInTime(popupType.startedDate, popupType.endedDate, at))
			.collect(Collectors.toList());
	}
}
