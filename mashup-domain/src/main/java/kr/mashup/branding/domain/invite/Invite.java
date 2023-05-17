package kr.mashup.branding.domain.invite;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

import com.sun.istack.NotNull;

import kr.mashup.branding.domain.BaseEntity;
import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.member.Platform;
import kr.mashup.branding.util.DateRange;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

	@Builder
	public Invite(String code, Platform platform, Generation generation, int limitCount, LocalDateTime startedAt,
		LocalDateTime endedAt) {
		this.code = code;
		this.platform = platform;
		this.generation = generation;
		this.limitCount = limitCount;
		this.startedAt = startedAt;
		this.endedAt = endedAt;
	}

	public static Invite of(Platform platform, Generation generation, DateRange dateRange) {
		int defaultLimitCount = 30;

		return Invite.builder()
			.code(generateCode(platform, generation.getNumber()))
			.platform(platform)
			.generation(generation)
			.limitCount(defaultLimitCount)
			.startedAt(dateRange.getStart())
			.endedAt(dateRange.getEnd())
			.build();
	}

	public void modifyValidCodeDateRange(DateRange dateRange){
		this.startedAt = dateRange.getStart();
		this.endedAt = dateRange.getEnd();
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
			char randomChar = (char)((Math.random() * 26) + 65);
			code.append(randomChar);
		}

		return code.toString();
	}
}
