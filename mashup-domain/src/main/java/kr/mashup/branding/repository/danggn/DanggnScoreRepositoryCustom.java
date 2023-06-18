package kr.mashup.branding.repository.danggn;

import java.util.List;

import kr.mashup.branding.domain.danggn.DanggnScore;
import kr.mashup.branding.repository.danggn.DanggnScoreRepositoryCustomImpl.DanggnScorePlatformQueryResult;

public interface DanggnScoreRepositoryCustom {
    List<DanggnScore> findOrderedListByGenerationNum(Integer generationNumber, Long danggnRankingRoundId);

    List<DanggnScorePlatformQueryResult> findOrderedDanggnScorePlatformListByGenerationNum(Integer generationNumber, Long danggnRankingRoundId);
}
