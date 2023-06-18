package kr.mashup.branding.repository.danggn;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.mashup.branding.domain.danggn.DanggnRankingRound;

public interface DanggnRankingRoundRepository extends JpaRepository<DanggnRankingRound, Long>, DanggnRankingRoundRepositoryCustom {

	List<DanggnRankingRound> findByGenerationId(Long generationId);

	Optional<DanggnRankingRound> findByNumberAndGenerationId(Integer number, Long generationId);
}
