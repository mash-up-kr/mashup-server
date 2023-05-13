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
    public String getCachedFirstRecordMemberId(Integer generationNumber) {
        return findFirstRecordMember(generationNumber).getId().toString();
    }

    @Cacheable(cacheNames = "danggnFirstPlaceRecord", key = "T(kr.mashup.branding.service.danggn.DanggnCacheKey).PLATFORM + #generationNumber.toString()")
    public String getCachedFirstRecordPlatform(Integer generationNumber) {
        return findFirstRecordPlatform(generationNumber).toString();
    }

    @CachePut(cacheNames = "danggnFirstPlaceRecord", key = "#key + #generationNumber.toString()")
    public String updateCachedFirstRecord(DanggnCacheKey key, Integer generationNumber, String value) {
        return value;
    }

    public Member findFirstRecordMember(Integer generationNumber) {
        List<DanggnScore> danggnScores = danggnScoreRepository.findOrderedListByGenerationNum(generationNumber);

        if (danggnScores.isEmpty()) {
            return null;
        }
        return danggnScores.get(0)
                .getMemberGeneration()
                .getMember();
    }

    public Platform findFirstRecordPlatform(Integer generationNumber) {
        List<DanggnScorePlatformQueryResult> results = danggnScoreRepository.findOrderedDanggnScorePlatformListByGenerationNum(generationNumber);

        if (results.isEmpty()) {
            return null;
        }
        return results.get(0)
                .getPlatform();
    }
}
