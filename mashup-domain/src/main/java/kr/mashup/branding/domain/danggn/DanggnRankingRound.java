package kr.mashup.branding.domain.danggn;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DanggnRankingRound {

	@Id
	@GeneratedValue
	private Long id;

	private Integer number;

	private LocalDateTime startedAt;

	private LocalDateTime endedAt;

	private Long generationId;

	public static DanggnRankingRound of(
		Integer number,
		LocalDateTime startedAt,
		LocalDateTime endedAt,
		Long generationId
	) {
		return new DanggnRankingRound(number, startedAt, endedAt, generationId);
	}

	private DanggnRankingRound(
		Integer number,
		LocalDateTime startedAt,
		LocalDateTime endedAt,
		Long generationId
	) {
		this.number = number;
		this.startedAt = startedAt;
		this.endedAt = endedAt;
		this.generationId = generationId;
	}
}
