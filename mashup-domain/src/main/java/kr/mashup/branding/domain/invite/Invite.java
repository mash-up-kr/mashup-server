package kr.mashup.branding.domain.invite;

import com.sun.istack.NotNull;
import kr.mashup.branding.domain.BaseEntity;
import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.member.Platform;
import kr.mashup.branding.util.DateRange;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Invite extends BaseEntity {
	@NotEmpty
	private String code;

	@NotNull
	@Enumerated(EnumType.STRING)
	private Platform platform;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "generation_id")
	private Generation generation;

	@NotNull
	private int limitCount;

	@NotNull
	private LocalDateTime startedAt;

	@NotNull
	private LocalDateTime endedAt;

	public static Invite of(Platform platform, Generation generation, DateRange dateRange) {
		int defaultLimitCount = 30;

		Invite invite = new Invite();
		invite.code = generateCode(platform, generation.getNumber());
		invite.platform = platform;
		invite.generation = generation;
		invite.limitCount = defaultLimitCount;
		invite.startedAt = dateRange.getStart();
		invite.endedAt = dateRange.getEnd();

		return invite;
	}

	/**
	 * Platform 앞에서 2글자 + 기수 숫자변환(0~999기) + 나머지 랜덤문자열(3글자 랜덤)
	 * ex) Spring, 8기 -> SP(Spring) + AAI(008기) + WEZ(랜덤 3글자) -> SPAAIWEZ
	 * ex) DESIGN, 12기 -> DE(Design) +ABC(012기) + FRD(랜덤 글자) -> DEABCFRD
	 *
	 */
	private static String generateCode(Platform platform, int generationNumber) {
		// Platform 앞 2글자 - 2글자
		StringBuilder code = new StringBuilder(platform.name().substring(0, 2));

		// 기수 숫자변환(0 ~ 999) - 3글자
		for (char ch : String.format("%03d", generationNumber).toCharArray()) {
			code.append((char)((int)ch + 17));
		}

		// 랜덤 문자열 - 3글자
		for (int i = 1; i <= 3; i++) {
			char randomChar = (char) ((Math.random() * 26) + 65);
			code.append(randomChar);
		}

		return code.toString();
	}
}
