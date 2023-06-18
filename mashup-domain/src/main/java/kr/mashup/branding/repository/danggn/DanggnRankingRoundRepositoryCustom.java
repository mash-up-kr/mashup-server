package kr.mashup.branding.repository.danggn;

import java.util.Optional;

import kr.mashup.branding.domain.danggn.DanggnRankingRound;

public interface DanggnRankingRoundRepositoryCustom {

	Optional<DanggnRankingRound> retrieveCurrentByGenerationNum(Integer generationNumber);
}
