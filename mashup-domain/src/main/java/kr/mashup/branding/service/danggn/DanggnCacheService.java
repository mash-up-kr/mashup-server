package kr.mashup.branding.service.danggn;

import kr.mashup.branding.domain.danggn.DanggnScore;
import kr.mashup.branding.repository.danggn.DanggnScoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.mashup.branding.repository.danggn.DanggnScoreRepositoryCustomImpl.DanggnScorePlatformQueryResult;

@Service
@RequiredArgsConstructor
public class DanggnCacheService {
    private final DanggnScoreRepository danggnScoreRepository;

    @Cacheable(cacheNames = "danggnFirstPlaceRecord", key = "T(kr.mashup.branding.service.danggn.DanggnCacheKey).MEMBER + #generationNumber.toString()")
    public String getCachedFirstRecordMemberId(Integer generationNumber) {
        return findFirstRecordMemberId(generationNumber);
    }

    @Cacheable(cacheNames = "danggnFirstPlaceRecord", key = "T(kr.mashup.branding.service.danggn.DanggnCacheKey).PLATFORM + #generationNumber.toString()")
    public String getCachedFirstRecordPlatform(Integer generationNumber) {
        return findFirstRecordPlatform(generationNumber);
    }

    @CachePut(cacheNames = "danggnFirstPlaceRecord", key = "#key + #generationNumber.toString()")
    public String updateCachedFirstRecord(DanggnCacheKey key, Integer generationNumber, String value) {
        return value;
    }

    public String findFirstRecordMemberId(Integer generationNumber) {
        List<DanggnScore> danggnScores = danggnScoreRepository.findOrderedListByGenerationNum(generationNumber);

        if (danggnScores.isEmpty()) {
            return null;
        }
        return danggnScores.get(0)
                .getMemberGeneration()
                .getMember()
                .getId()
                .toString();
    }

    public String findFirstRecordPlatform(Integer generationNumber) {
        List<DanggnScorePlatformQueryResult> results = danggnScoreRepository.findOrderedDanggnScorePlatformListByGenerationNum(generationNumber);

        if (results.isEmpty()) {
            return null;
        }
        return results.get(0)
                .getPlatform()
                .toString();
    }
}
