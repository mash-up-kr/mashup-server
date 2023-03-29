package kr.mashup.branding.service.danggn;

import kr.mashup.branding.domain.danggn.DanggnScore;
import kr.mashup.branding.domain.member.MemberGeneration;
import kr.mashup.branding.repository.danggn.DanggnScoreRepository;
import kr.mashup.branding.repository.danggn.DanggnScoreRepositoryCustomImpl.DanggnScorePlatformQueryResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DanggnScoreService {
    private final DanggnScoreRepository danggnScoreRepository;

    public DanggnScore findByMemberGeneration(MemberGeneration memberGeneration) {
        return danggnScoreRepository.findByMemberGeneration(memberGeneration)
            .orElseGet(() -> danggnScoreRepository.save(DanggnScore.of(memberGeneration, 0L)));
    }

    public List<DanggnScore> getDanggnScoreOrderedList(Integer generationNumber, Integer limit) {
        return danggnScoreRepository.findOrderedListByGenerationNum(generationNumber, limit);
    }

    public List<DanggnScorePlatformQueryResult> getDanggnScorePlatformOrderedList(Integer generationNumber) {
        return danggnScoreRepository.findOrderedDanggnScorePlatformListByGenerationNum(generationNumber);
    }
}
