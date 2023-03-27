package kr.mashup.branding.repository.danggn;

import kr.mashup.branding.domain.danggn.DanggnScore;

import java.util.List;

public interface DanggnScoreRepositoryCustom {
    List<DanggnScore> findTopByGenerationNumByTotalShakeScore(Integer generationNumber, Integer limit);

    List<DanggnScore> findAllByGenerationNumber(Integer generationNumber);
}