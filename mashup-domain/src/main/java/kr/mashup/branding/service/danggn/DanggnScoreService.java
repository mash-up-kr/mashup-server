package kr.mashup.branding.service.danggn;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.mashup.branding.domain.danggn.DanggnScore;
import kr.mashup.branding.domain.member.MemberGeneration;
import kr.mashup.branding.repository.danggn.DanggnScoreRepository;
import kr.mashup.branding.repository.danggn.DanggnScoreRepositoryCustomImpl.DanggnScorePlatformQueryResult;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DanggnScoreService {
    private final DanggnScoreRepository danggnScoreRepository;

    public DanggnScore findByMemberGenerationOrSave(MemberGeneration memberGeneration, Long danggnRankingRoundId) {
        return danggnScoreRepository.findByMemberGenerationAndDanggnRankingRoundId(memberGeneration, danggnRankingRoundId)
            .orElseGet(() -> danggnScoreRepository.save(DanggnScore.of(memberGeneration, 0L, danggnRankingRoundId)));
    }

    public List<DanggnScore> getDanggnScoreOrderedList(Integer generationNumber, Long danggnRankingRoundId) {
        return danggnScoreRepository.findOrderedListByGenerationNum(generationNumber, danggnRankingRoundId);
    }

    public List<DanggnScorePlatformQueryResult> getDanggnScorePlatformOrderedList(Integer generationNumber, Long danggnRankingRoundId) {
        return danggnScoreRepository.findOrderedDanggnScorePlatformListByGenerationNum(generationNumber, danggnRankingRoundId);
    }
}
