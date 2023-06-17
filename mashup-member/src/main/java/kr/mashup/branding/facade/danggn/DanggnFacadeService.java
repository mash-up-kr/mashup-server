package kr.mashup.branding.facade.danggn;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.mashup.branding.domain.danggn.DanggnRankingReward;
import kr.mashup.branding.domain.danggn.DanggnRankingRound;
import kr.mashup.branding.domain.danggn.DanggnScore;
import kr.mashup.branding.domain.danggn.DanggnTodayMessage;
import kr.mashup.branding.domain.member.MemberGeneration;
import kr.mashup.branding.domain.member.Platform;
import kr.mashup.branding.service.danggn.DanggnRankingRewardService;
import kr.mashup.branding.service.danggn.DanggnRankingRoundService;
import kr.mashup.branding.service.danggn.DanggnScoreService;
import kr.mashup.branding.service.danggn.DanggnShakeLogService;
import kr.mashup.branding.service.danggn.DanggnTodayMessageService;
import kr.mashup.branding.service.member.MemberService;
import kr.mashup.branding.ui.danggn.response.DanggnMemberRankData;
import kr.mashup.branding.ui.danggn.response.DanggnPlatformRankResponse;
import kr.mashup.branding.ui.danggn.response.DanggnRandomMessageResponse;
import kr.mashup.branding.ui.danggn.response.DanggnRankingRoundResponse;
import kr.mashup.branding.ui.danggn.response.DanggnRankingRoundResponse.DanggnRankingRewardResponse;
import kr.mashup.branding.ui.danggn.response.DanggnRankingRoundsResponse;
import kr.mashup.branding.ui.danggn.response.DanggnScoreResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DanggnFacadeService {
    @Value("${golden-danggn-percent}")
    private Integer goldenDanggnPercent;
    private final MemberService memberService;

    private final DanggnShakeLogService danggnShakeLogService;

    private final DanggnScoreService danggnScoreService;

    private final DanggnTodayMessageService danggnTodayMessageService;

    private final DanggnRankingRoundService danggnRankingRoundService;

    private final DanggnRankingRewardService danggnRankingRewardService;

    @Transactional
    public DanggnScoreResponse addScore(Long memberGenerationId, Long score) {
        final MemberGeneration memberGeneration = memberService.findByMemberGenerationId(memberGenerationId);
        DanggnScore danggnScore = danggnScoreService.findByMemberGeneration(memberGeneration);
        danggnScore.addScore(score);
        danggnShakeLogService.createLog(memberGeneration, score);
        return DanggnScoreResponse.of(danggnScore.getTotalShakeScore());
    }

    @Transactional(readOnly = true)
    public List<DanggnMemberRankData> getMemberRankList(Integer generationNumber) {
        return danggnScoreService.getDanggnScoreOrderedList(generationNumber)
            .stream().map(DanggnMemberRankData::from).collect(Collectors.toList());
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

    public Integer getGoldenDanggnPercent() {
        return goldenDanggnPercent;
    }

    public DanggnRandomMessageResponse getRandomTodayMessage() {
        List<DanggnTodayMessage> danggnTodayMessageList = danggnTodayMessageService.findAll();
        Collections.shuffle(danggnTodayMessageList);
        return DanggnRandomMessageResponse.from(danggnTodayMessageList.get(0));
    }

    @Transactional
    public DanggnRankingRoundResponse getRankingRoundById(Long danggnRankingRoundId) {
        final DanggnRankingRound currentDanggnRankingRound = danggnRankingRoundService.findByIdOrThrow(danggnRankingRoundId);
        final Optional<DanggnRankingRound> previousDanggnRankingRound = danggnRankingRoundService.getPreviousById(danggnRankingRoundId);

        final DanggnRankingRewardResponse rewardResponse = getDanggnRankingRewardResponse(previousDanggnRankingRound);
        return DanggnRankingRoundResponse.of(currentDanggnRankingRound, rewardResponse);
    }

    public DanggnRankingRoundsResponse getAllRankingRoundByMemberGeneration(Long memberGenerationId) {
        final MemberGeneration memberGeneration = memberService.findByMemberGenerationId(memberGenerationId);
        List<DanggnRankingRoundsResponse.DanggnRankingRoundSimpleResponse> simpleResponses = danggnRankingRoundService.findPastAndCurrentByGeneration(memberGeneration.getGeneration())
            .stream()
            .map(DanggnRankingRoundsResponse.DanggnRankingRoundSimpleResponse::from)
            .collect(Collectors.toList());
        return DanggnRankingRoundsResponse.from(simpleResponses);
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

    private DanggnRankingRewardResponse getDanggnRankingRewardResponse(Optional<DanggnRankingRound> danggnRankingRound) {

        final DanggnRankingReward danggnRankingReward = danggnRankingRound.isPresent() ? danggnRankingRewardService.findByDanggnRankingRoundOrNull(danggnRankingRound) : null;

        String name = danggnRankingReward != null ?
            memberService.getActiveOrThrowById(danggnRankingReward.getFirstPlaceRecordMemberId()).getName() : null;
        String comment = danggnRankingReward != null ? danggnRankingReward.getComment() : null;
        return DanggnRankingRewardResponse.of(name, comment);
    }
}
