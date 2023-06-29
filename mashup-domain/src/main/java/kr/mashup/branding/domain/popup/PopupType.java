package kr.mashup.branding.domain.popup;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import kr.mashup.branding.util.DateUtil;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum PopupType {

	DANGGN_REWARD(LocalDate.MIN, LocalDate.MAX),
	DANGGN(LocalDate.of(2023, 5, 18), LocalDate.of(2023, 6, 2)),			// 당근 흔들기 릴리즈 팝업 타입
	DANGGN_UPDATE(LocalDate.of(2023, 6, 26), LocalDate.of(2023, 7, 26)), 	// 당근 흔들기 업데이트 팝업 타입 FIXME: 배포 일자에 맞춰서, 노출 일자 수정 필요
	;

	private final LocalDate startedDate;
	private final LocalDate endedDate;

	public static List<PopupType> findActives(LocalDate at) {
		return Stream.of(PopupType.values())
			.filter(popupType -> DateUtil.isInTime(popupType.startedDate, popupType.endedDate, at))
			.collect(Collectors.toList());
	}
}
