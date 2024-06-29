package kr.mashup.branding.facade.danggn;

import kr.mashup.branding.domain.danggn.*;
import kr.mashup.branding.domain.danggn.Exception.DanggnRankingRewardAlreadyWrittenException;
import kr.mashup.branding.domain.danggn.Exception.DanggnRankingRewardNotAllowedException;
import kr.mashup.branding.domain.mashong.MissionStrategyType;
import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.member.MemberGeneration;
import kr.mashup.branding.domain.member.Platform;
import kr.mashup.branding.domain.pushnoti.vo.DanggnRewardUpdatedVo;
import kr.mashup.branding.facade.mashong.MashongMissionFacadeService;
import kr.mashup.branding.infrastructure.pushnoti.PushNotiEventPublisher;
import kr.mashup.branding.service.danggn.*;
import kr.mashup.branding.service.member.MemberService;
import kr.mashup.branding.ui.danggn.response.*;
import kr.mashup.branding.ui.danggn.response.DanggnRankingRoundResponse.DanggnRankingRewardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    private final PushNotiEventPublisher pushNotiEventPublisher;

    private final MashongMissionFacadeService mashongMissionFacadeService;

    @Transactional
    public DanggnScoreResponse addScore(Long memberGenerationId, Long score) {
        final MemberGeneration memberGeneration = memberService.findByMemberGenerationId(memberGenerationId);
        final Long currentDanggnRankingRoundId = danggnRankingRoundService.findCurrentByGeneration(memberGeneration.getGeneration().getNumber()).getId();

        DanggnScore danggnScore = danggnScoreService.findByMemberGenerationOrSave(memberGeneration, currentDanggnRankingRoundId);
        danggnScore.addScore(score);
        danggnShakeLogService.createLog(memberGeneration, score);

        //todo: application event publisher 로 변경
        mashongMissionFacadeService.apply(MissionStrategyType.MASHONG_DANGGN_SHAKE_INDIVIDUAL, memberGeneration, score.doubleValue());
        mashongMissionFacadeService.apply(MissionStrategyType.MASHONG_DANGGN_SHAKE_TEAM, memberGeneration, score.doubleValue());
        return DanggnScoreResponse.of(danggnScore.getTotalShakeScore());
    }

    @Transactional(readOnly = true)
    public List<DanggnMemberRankData> getMemberRankList(Integer generationNumber, Long danggnRankingRoundId) {
        if (danggnRankingRoundId == null) { // danggnRankingRoundId 가 없으면 현재 운영중인 회차 아이디 반환
            danggnRankingRoundId = danggnRankingRoundService.findCurrentByGeneration(generationNumber).getId();
        }

        return danggnScoreService.getDanggnScoreOrderedList(generationNumber, danggnRankingRoundId)
            .stream().map(DanggnMemberRankData::from).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<DanggnPlatformRankResponse> getPlatformRankList(Integer generationNumber, Long danggnRankingRoundId) {
        if (danggnRankingRoundId == null) { // danggnRankingRoundId 가 없으면 현재 운영중인 회차 아이디 반환
            danggnRankingRoundId = danggnRankingRoundService.findCurrentByGeneration(generationNumber).getId();
        }

        List<DanggnPlatformRankResponse> existingPlatformRankList = getExistingPlatformRankList(generationNumber, danggnRankingRoundId);

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
    public DanggnRankingRoundsResponse getAllRankingRoundByMemberGeneration(Long memberGenerationId) {
        final MemberGeneration memberGeneration = memberService.findByMemberGenerationId(memberGenerationId);
        List<DanggnRankingRoundsResponse.DanggnRankingRoundSimpleResponse> simpleResponses = danggnRankingRoundService.findPastAndCurrentByGeneration(memberGeneration.getGeneration())
            .stream()
            .map(DanggnRankingRoundsResponse.DanggnRankingRoundSimpleResponse::from)
            .collect(Collectors.toList());
        return DanggnRankingRoundsResponse.from(simpleResponses);
    }

    @Transactional
    public DanggnRankingRoundResponse getRankingRoundById(Long memberId, Long danggnRankingRoundId) {
        final DanggnRankingRound currentDanggnRankingRound = danggnRankingRoundService.findByIdOrThrow(danggnRankingRoundId);
        final Optional<DanggnRankingRound> previousDanggnRankingRound = danggnRankingRoundService.getPreviousById(danggnRankingRoundId);

        // 조회하는 회차의 전 회차 1등 공지사항 조회
        final DanggnRankingRewardResponse rewardResponse = getDanggnRankingRewardResponse(memberId, previousDanggnRankingRound);
        return DanggnRankingRoundResponse.of(currentDanggnRankingRound, rewardResponse);
    }

    @Transactional
    public void writeDanggnRankingRewardComment(
        Long memberId,
        Long danggnRankingRewardId,
        String comment
    ) {
        DanggnRankingReward danggnRankingReward = danggnRankingRewardService.findById(danggnRankingRewardId);
        if(danggnRankingReward.getComment() != null) {
            throw new DanggnRankingRewardAlreadyWrittenException();
        }
        if(danggnRankingReward.getFirstPlaceRecordMemberId() != memberId) {
            throw new DanggnRankingRewardNotAllowedException();
        }
        danggnRankingRewardService.writeComment(danggnRankingRewardId, comment);

        DanggnRankingRound danggnRankingRound = danggnRankingRoundService.findByIdOrThrow(danggnRankingReward.getDanggnRankingRoundId());
        Member member = memberService.getActiveOrThrowById(danggnRankingReward.getFirstPlaceRecordMemberId());
        pushNotiEventPublisher.publishPushNotiSendEvent(
                new DanggnRewardUpdatedVo(
                        danggnRankingRound.getNumber(),
                        member,
                        memberService.getAllDanggnPushNotiTargetableMembers()
                )
        );
    }

    private List<DanggnPlatformRankResponse> getExistingPlatformRankList(Integer generationNumber, Long danggnRankingRoundId) {
        return danggnScoreService.getDanggnScorePlatformOrderedList(generationNumber, danggnRankingRoundId).stream()
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

    private DanggnRankingRewardResponse getDanggnRankingRewardResponse(Long memberId, Optional<DanggnRankingRound> danggnRankingRound) {

        final DanggnRankingReward reward = danggnRankingRewardService.findByDanggnRankingRoundOrNull(danggnRankingRound);
        final DanggnRankingRewardStatus status =
            (reward == null || reward.getFirstPlaceRecordMemberId() == null) ? DanggnRankingRewardStatus.FIRST_PLACE_MEMBER_NOT_EXISTED : reward.getRankingRewardStatus();

        final Member member = status.hasFirstPlaceMember() ? memberService.getActiveOrThrowById(reward.getFirstPlaceRecordMemberId()) : null;
        final Boolean isFirstPlaceMember = member != null && Objects.equals(memberId, member.getId());
        return DanggnRankingRewardResponse.of(reward, member, status, isFirstPlaceMember);
    }
}
