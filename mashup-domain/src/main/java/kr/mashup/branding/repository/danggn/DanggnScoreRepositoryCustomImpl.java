package kr.mashup.branding.repository.danggn;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.mashup.branding.domain.danggn.DanggnScore;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class DanggnScoreRepositoryCustomImpl implements DanggnScoreRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<DanggnScore> findTopByGenerationNumByTotalShakeScore(Integer limit, Integer generationNumber) {
        return null;
    }

    @Override
    public List<DanggnScore> findAll() {
        return null;
    }
}

