package kr.mashup.branding.repository.danggn;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.mashup.branding.domain.danggn.DanggnScore;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static kr.mashup.branding.domain.danggn.QDanggnScore.danggnScore;
import static kr.mashup.branding.domain.generation.QGeneration.generation;
import static kr.mashup.branding.domain.member.QMemberGeneration.memberGeneration;

@RequiredArgsConstructor
public class DanggnScoreRepositoryCustomImpl implements DanggnScoreRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<DanggnScore> findTopByGenerationNumByTotalShakeScore(Integer generationNumber, Integer limit) {
        return queryFactory.selectFrom(danggnScore)
            .innerJoin(danggnScore.memberGeneration, memberGeneration)
            .innerJoin(memberGeneration.generation, generation)
            .on(generation.number.eq(generationNumber))
            .orderBy(danggnScore.totalShakeScore.desc(), danggnScore.lastShakedAt.asc())
            .limit(limit)
            .fetch();
    }

    @Override
    public List<DanggnScore> findAllByGenerationNumber(Integer generationNumber) {
        return queryFactory.selectFrom(danggnScore)
            .innerJoin(danggnScore.memberGeneration, memberGeneration)
            .innerJoin(memberGeneration.generation, generation)
            .on(generation.number.eq(generationNumber))
            .fetch();
    }
}

