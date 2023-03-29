package kr.mashup.branding.repository.danggn;

import kr.mashup.branding.domain.danggn.DanggnScore;
import kr.mashup.branding.repository.danggn.DanggnScoreRepositoryCustomImpl.DanggnScorePlatformQueryResult;

import java.util.List;

public interface DanggnScoreRepositoryCustom {
    List<DanggnScore> findOrderedListByGenerationNum(Integer generationNumber);

    List<DanggnScorePlatformQueryResult> findOrderedDanggnScorePlatformListByGenerationNum(Integer generationNumber);
}