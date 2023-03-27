package kr.mashup.branding.facade.danggn;

import kr.mashup.branding.domain.danggn.DanggnScore;
import kr.mashup.branding.domain.member.MemberGeneration;
import kr.mashup.branding.domain.member.Platform;
import kr.mashup.branding.service.danggn.DanggnScoreService;
import kr.mashup.branding.service.danggn.DanggnShakeLogService;
import kr.mashup.branding.service.member.MemberService;
import kr.mashup.branding.ui.danggn.response.DanggnMemberRankResponse;
import kr.mashup.branding.ui.danggn.response.DanggnPlatformRankResponse;
import kr.mashup.branding.ui.danggn.response.DanggnScoreResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class DanggnFacadeService {
    private final MemberService memberService;

    private final DanggnShakeLogService danggnShakeLogService;

    private final DanggnScoreService danggnScoreService;


    @Transactional
    public DanggnScoreResponse addScore(
        Long memberId,
        Integer generationNumber,
        Long score
    ) {
        final MemberGeneration memberGeneration = memberService.findByMemberIdAndGenerationNumber(memberId, generationNumber);
        DanggnScore danggnScore = danggnScoreService.findByMemberGeneration(memberGeneration);
        danggnScore.addScore(score);
        danggnShakeLogService.createLog(memberGeneration, score);
        return DanggnScoreResponse.of(danggnScore.getTotalShakeScore());
    }

    @Transactional(readOnly = true)
    public List<DanggnMemberRankResponse> getMemberRankList(Integer generationNumber, Integer limit) {
        return danggnScoreService.getDanggnScoreOrderedList(generationNumber, limit)
            .stream().map(DanggnMemberRankResponse::from).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<DanggnPlatformRankResponse> getPlatformRankList(Integer generationNumber) {
        List<DanggnPlatformRankResponse> existingPlatformRankList = getExistingPlatformRankList(generationNumber);

        Set<Platform> notExistingPlatforms = getNotExistingPlatforms(existingPlatformRankList);
        List<DanggnPlatformRankResponse> notExistingPlatformRankList = notExistingPlatforms.stream()
            .map(platform -> DanggnPlatformRankResponse.of(platform, 0L))
            .collect(Collectors.toList());

        return Stream.concat(existingPlatformRankList.stream(), notExistingPlatformRankList.stream()).collect(Collectors.toList());
    }

    private List<DanggnPlatformRankResponse> getExistingPlatformRankList(Integer generationNumber) {
        return danggnScoreService.getDanggnScorePlatformOrderedList(generationNumber).stream()
            .map(queryResult -> DanggnPlatformRankResponse.of(queryResult.getPlatform(), queryResult.getTotalScore()))
            .collect(Collectors.toList());
    }

    private Set<Platform> getNotExistingPlatforms(List<DanggnPlatformRankResponse> existingPlatformRankList) {
        Set<Platform> entirePlatforms = Arrays.stream(Platform.values()).collect(Collectors.toSet());
        Set<Platform> existingPlatforms = existingPlatformRankList.stream()
            .map(DanggnPlatformRankResponse::getPlatform)
            .collect(Collectors.toSet());
        entirePlatforms.removeAll(existingPlatforms);
        return entirePlatforms;
    }
}