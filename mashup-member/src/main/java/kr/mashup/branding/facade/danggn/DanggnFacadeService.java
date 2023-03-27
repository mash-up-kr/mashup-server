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

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.summingLong;

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
    public List<DanggnMemberRankResponse> getMemberRankList(
        Integer generationNumber,
        Integer limit
    ) {
        return danggnScoreService.getDanggnScoreOrderedList(generationNumber, limit)
            .stream().map(DanggnMemberRankResponse::from).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<DanggnPlatformRankResponse> getPlatformRankList(
        Integer generationNumber
    ) {
        Map<Platform, Long> danggnScoreMap = groupDanggnScoresByPlatform(danggnScoreService.findAllByGenerationNumber(generationNumber));

        return Stream.of(Platform.values())
            .map(platform ->
                DanggnPlatformRankResponse.of(platform, danggnScoreMap.getOrDefault(platform, 0L))
            )
            .sorted(Comparator.comparing(DanggnPlatformRankResponse::getTotalShakeScore).reversed())
            .collect(Collectors.toList());
    }

    private Map<Platform, Long> groupDanggnScoresByPlatform(List<DanggnScore> danggnScoreList) {
        return danggnScoreList.stream().collect(Collectors.groupingBy(
            danggnScore -> danggnScore.getMemberGeneration().getPlatform(),
            summingLong(DanggnScore::getTotalShakeScore)
        ));
    }
}