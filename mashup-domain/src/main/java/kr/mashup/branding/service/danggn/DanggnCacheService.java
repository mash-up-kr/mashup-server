package kr.mashup.branding.service.danggn;

import static kr.mashup.branding.repository.danggn.DanggnScoreRepositoryCustomImpl.*;

import java.util.List;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import kr.mashup.branding.domain.danggn.DanggnScore;
import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.member.Platform;
import kr.mashup.branding.repository.danggn.DanggnScoreRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DanggnCacheService {
    private final DanggnScoreRepository danggnScoreRepository;

    @Cacheable(cacheNames = "danggnFirstPlaceRecord", key = "T(kr.mashup.branding.service.danggn.DanggnCacheKey).MEMBER + #generationNumber.toString()")
    public String getCachedFirstRecordMemberId(Integer generationNumber, Long danggnRankingRoundId) {
        return findFirstRecordMember(generationNumber, danggnRankingRoundId).getId().toString();
    }

    @Cacheable(cacheNames = "danggnFirstPlaceRecord", key = "T(kr.mashup.branding.service.danggn.DanggnCacheKey).PLATFORM + #generationNumber.toString()")
    public String getCachedFirstRecordPlatform(Integer generationNumber, Long danggnRankingRoundId) {
        return findFirstRecordPlatform(generationNumber, danggnRankingRoundId).toString();
    }

    @CachePut(cacheNames = "danggnFirstPlaceRecord", key = "#key + #generationNumber.toString()")
    public String updateCachedFirstRecord(DanggnCacheKey key, Integer generationNumber, String value) {
        return value;
    }

    public Member findFirstRecordMember(Integer generationNumber, Long danggnRankingRoundId) {
        List<DanggnScore> danggnScores = danggnScoreRepository.findOrderedListByGenerationNum(generationNumber, danggnRankingRoundId);

        if (danggnScores.isEmpty()) {
            return null;
        }
        return danggnScores.get(0)
                .getMemberGeneration()
                .getMember();
    }

    public Platform findFirstRecordPlatform(Integer generationNumber, Long danggnRankingRoundId) {
        List<DanggnScorePlatformQueryResult> results = danggnScoreRepository.findOrderedDanggnScorePlatformListByGenerationNum(generationNumber, danggnRankingRoundId);

        if (results.isEmpty()) {
            return null;
        }
        return results.get(0)
                .getPlatform();
    }
}
