package kr.mashup.branding.repository.danggn;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.mashup.branding.domain.danggn.DanggnScore;
import kr.mashup.branding.domain.member.Platform;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static kr.mashup.branding.domain.danggn.QDanggnScore.danggnScore;
import static kr.mashup.branding.domain.generation.QGeneration.generation;
import static kr.mashup.branding.domain.member.QMemberGeneration.memberGeneration;

@RequiredArgsConstructor
public class DanggnScoreRepositoryCustomImpl implements DanggnScoreRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<DanggnScore> findOrderedListByGenerationNum(Integer generationNumber, Integer limit) {
        return queryFactory.selectFrom(danggnScore)
            .innerJoin(danggnScore.memberGeneration, memberGeneration)
            .innerJoin(memberGeneration.generation, generation)
            .on(generation.number.eq(generationNumber))
            .orderBy(danggnScore.totalShakeScore.desc(), danggnScore.lastShakedAt.asc())
            .limit(limit)
            .fetch();
    }

    @Override
    public List<DanggnScorePlatformQueryResult> findOrderedDanggnScorePlatformListByGenerationNum(Integer generationNumber) {
        return queryFactory
            .select(
                memberGeneration.platform,
                danggnScore.totalShakeScore.sum(),
                danggnScore.lastShakedAt.max()
            )
            .from(danggnScore)
            .innerJoin(danggnScore.memberGeneration, memberGeneration)
            .groupBy(memberGeneration.platform)
            .orderBy(
                danggnScore.totalShakeScore.sum().desc(),
                danggnScore.lastShakedAt.max().asc()
            )
            .fetch()
            .stream()
            .map(tuple -> new DanggnScorePlatformQueryResult(
                tuple.get(memberGeneration.platform),
                tuple.get(danggnScore.totalShakeScore.sum()),
                tuple.get(danggnScore.lastShakedAt.max())
            ))
            .collect(Collectors.toList());
    }

    @Getter
    @RequiredArgsConstructor
    public static class DanggnScorePlatformQueryResult {
        private final Platform platform;

        private final Long totalScore;

        private final LocalDateTime lastShakedAt;
    }
}

