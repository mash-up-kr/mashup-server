package kr.mashup.branding.repository.danggn;

import static kr.mashup.branding.domain.danggn.QDanggnScore.*;
import static kr.mashup.branding.domain.generation.QGeneration.*;
import static kr.mashup.branding.domain.member.QMemberGeneration.*;

import java.util.List;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import kr.mashup.branding.domain.danggn.DanggnScore;
import kr.mashup.branding.domain.member.Platform;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DanggnScoreRepositoryCustomImpl implements DanggnScoreRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<DanggnScore> findOrderedListByGenerationNum(Integer generationNumber, Long danggnRankingRoundId) {
        return queryFactory.selectFrom(danggnScore)
            .innerJoin(danggnScore.memberGeneration, memberGeneration)
            .innerJoin(memberGeneration.generation, generation)
            .on(generation.number.eq(generationNumber))
            .where(danggnScore.danggnRankingRoundId.eq(danggnRankingRoundId))
            .orderBy(danggnScore.totalShakeScore.desc(), danggnScore.lastShakedAt.asc())
            .fetch();
    }

    @Override
    public List<DanggnScorePlatformQueryResult> findOrderedDanggnScorePlatformListByGenerationNum(Integer generationNumber, Long danggnRankingRoundId) {
        return queryFactory
            .select(
                Projections.fields(DanggnScorePlatformQueryResult.class,
                    memberGeneration.platform.as("platform"),
                    danggnScore.totalShakeScore.sum().as("totalScore")
                )
            )
            .from(danggnScore)
            .innerJoin(danggnScore.memberGeneration, memberGeneration)
            .innerJoin(memberGeneration.generation, generation)
            .on(generation.number.eq(generationNumber))
            .where(danggnScore.danggnRankingRoundId.eq(danggnRankingRoundId))
            .groupBy(memberGeneration.platform)
            .orderBy(
                danggnScore.totalShakeScore.sum().desc(),
                danggnScore.lastShakedAt.max().asc()
            )
            .fetch();
    }

    @Getter
    @RequiredArgsConstructor
    public static class DanggnScorePlatformQueryResult {
        Platform platform;

        Long totalScore;
    }
}

