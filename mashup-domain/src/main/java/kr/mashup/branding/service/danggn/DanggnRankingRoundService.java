package kr.mashup.branding.service.danggn;

import kr.mashup.branding.domain.danggn.DanggnRankingRound;
import kr.mashup.branding.domain.danggn.Exception.DanggnRankingRoundNotFoundException;
import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.repository.danggn.DanggnRankingRoundRepository;
import kr.mashup.branding.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DanggnRankingRoundService {

	private final DanggnRankingRoundRepository danggnRankingRoundRepository;

	public DanggnRankingRound findCurrentByGeneration(Integer generationNumber) {
		return danggnRankingRoundRepository.retrieveCurrentByGenerationNum(generationNumber)
			.orElseThrow(DanggnRankingRoundNotFoundException::new);
	}

	public DanggnRankingRound findByIdOrThrow(Long id) {
		return danggnRankingRoundRepository.findById(id)
			.orElseThrow(DanggnRankingRoundNotFoundException::new);
	}

	public Optional<DanggnRankingRound> getPreviousById(Long id) {
		DanggnRankingRound current =  danggnRankingRoundRepository.findById(id)
			.orElseThrow(DanggnRankingRoundNotFoundException::new);
		return danggnRankingRoundRepository.findByNumberAndGenerationId(current.getNumber() - 1, current.getGenerationId());
	}

	public List<DanggnRankingRound> findPastAndCurrentByGeneration(Generation generation) {
		return danggnRankingRoundRepository.findByGenerationId(generation.getId())
			.stream()
			.filter(round -> DateUtil.isStartBeforeOrEqualEnd(round.getStartedAt(), LocalDateTime.now()))
			.sorted(Comparator.comparingInt(DanggnRankingRound::getNumber).reversed())
			.collect(Collectors.toList());
	}

	public List<DanggnRankingRound> getAllSorted() {
		return danggnRankingRoundRepository.findAll().stream()
			.sorted(Comparator.comparingInt(DanggnRankingRound::getNumber).reversed())
			.collect(Collectors.toList());
	}

	public void save(DanggnRankingRound danggnRankingRound) {
		danggnRankingRoundRepository.save(danggnRankingRound);
	}
}
