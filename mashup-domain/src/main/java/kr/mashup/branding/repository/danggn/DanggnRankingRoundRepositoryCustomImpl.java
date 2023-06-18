package kr.mashup.branding.repository.danggn;

import static kr.mashup.branding.domain.danggn.QDanggnRankingRound.*;
import static kr.mashup.branding.domain.generation.QGeneration.*;

import java.time.LocalDateTime;
import java.util.Optional;

import com.querydsl.jpa.impl.JPAQueryFactory;

import kr.mashup.branding.domain.danggn.DanggnRankingRound;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DanggnRankingRoundRepositoryCustomImpl implements DanggnRankingRoundRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public Optional<DanggnRankingRound> retrieveCurrentByGenerationNum(Integer generationNumber) {

		LocalDateTime now = LocalDateTime.now();

		return Optional.ofNullable(queryFactory.selectFrom(danggnRankingRound)
			.innerJoin(generation)
			.on(danggnRankingRound.generationId.eq(generation.id))
			.where(generation.number.eq(generationNumber)
				.and(danggnRankingRound.startedAt.loe(now)
					.and(danggnRankingRound.endedAt.goe(now))))
			.fetchFirst());
	}
}
