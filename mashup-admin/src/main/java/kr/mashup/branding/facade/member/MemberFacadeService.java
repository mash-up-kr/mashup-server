package kr.mashup.branding.facade.member;

import java.time.ZoneOffset;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import kr.mashup.branding.domain.member.*;
import kr.mashup.branding.service.member.CurrentMemberStatusCalculationService;
import kr.mashup.branding.ui.member.request.MemberDropOutRequest;
import kr.mashup.branding.ui.member.request.MemberStatusUpdateRequest;
import kr.mashup.branding.ui.member.request.MemberTransferRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.repository.member.MemberRepositoryCustomImpl.MemberScoreQueryResult;
import kr.mashup.branding.service.generation.GenerationService;
import kr.mashup.branding.service.member.MemberService;
import kr.mashup.branding.service.scorehistory.ScoreHistoryService;
import kr.mashup.branding.ui.member.response.MemberDetailResponse;
import kr.mashup.branding.ui.member.response.MemberResponse;
import kr.mashup.branding.ui.scorehistory.response.ScoreHistoryResponse;
import lombok.RequiredArgsConstructor;


@Service
@Transactional
@RequiredArgsConstructor
public class MemberFacadeService {

    private final MemberService memberService;
    private final GenerationService generationService;
    private final ScoreHistoryService scoreHistoryService;
    private final CurrentMemberStatusCalculationService currentMemberStatusCalculationService;

    @Transactional(readOnly = true)
    public Page<MemberResponse> getAll(
        final Integer generationNumber,
        final Platform platform,
        final String searchName,
        final Pageable pageable
    ) {

        final Generation generation = generationService.getByNumberOrThrow(generationNumber);

        final Page<MemberScoreQueryResult> queryResults
                = memberService.getActiveAllByGeneration(generation, platform, searchName, pageable);

        final List<MemberResponse> response = new ArrayList<>();
        final List<Member> members =
            queryResults
                .getContent()
                .stream()
                .map(MemberScoreQueryResult::getMember)
                .collect(Collectors.toList());
        final Map<Long, CurrentMemberStatus> currentStatus =
            currentMemberStatusCalculationService.getCurrentStatus(generation, members);

        for (final MemberScoreQueryResult result : queryResults) {

            final Member member = result.getMember();
            final MemberGeneration memberGeneration = result.getMemberGeneration();

            final MemberResponse memberResponse = MemberResponse.of(
                member.getId(),
                member.getName(),
                member.getIdentification(),
                memberGeneration.getPlatform().name(),
                result.getScore(),
                currentStatus.get(member.getId()));

            response.add(memberResponse);
        }

        return new PageImpl<>(response,pageable, queryResults.getTotalElements());
    }


    @Transactional(readOnly = true)
    public MemberDetailResponse getAttendance(Integer generationNumber, Long memberId) {
        // 이름 아이디 기수 플랫폼, 총활동점수
        // 활동점수 히스토리
        // 제목, 세미나정보, 등록일시, 점수, 총활동점수,
        final Generation generation = generationService.getByNumberOrThrow(generationNumber);

        final Member member = memberService.findMemberById(memberId);

        final String memberName = member.getName();

        final String identification = member.getIdentification();

        final Platform platform = memberService.getPlatform(member, generation);

        final AtomicReference<Double> accumulatedScore = new AtomicReference<>(0.0);

        final List<ScoreHistoryResponse> scoreHistories = scoreHistoryService
            .getByMemberAndGeneration(member, generation)
            .stream()
            .sorted(Comparator.comparingLong(it -> it.getDate().toInstant(ZoneOffset.UTC).getEpochSecond()))
            .map(it -> {
                if (!it.isCanceled()) {
                    accumulatedScore.updateAndGet(v -> v + it.getScore());
                }
                return ScoreHistoryResponse.from(it, accumulatedScore.get());
            })
            .collect(Collectors.toList());

        Collections.reverse(scoreHistories);

        return MemberDetailResponse.of(memberName, identification, generationNumber, platform.name(), scoreHistories, member.getStatus());
    }

    @Transactional
    public void resetPassword(
        final String identification,
        final String newPassword
    ) {
        memberService.resetPassword(identification, newPassword);
    }

    @Transactional
    public void withdraw(Long memberId) {
        memberService.deleteMember(memberId);
    }

    @Transactional
    public void updateMemberStatus(Integer generationNumber, MemberStatusUpdateRequest memberStatusUpdateRequest) {

        final Generation generation = generationService.getByNumberOrThrow(generationNumber);

        final MemberStatus memberStatus = memberStatusUpdateRequest.getMemberStatus();

        final List<Long> memberIds = memberStatusUpdateRequest.getMemberIds();

        final List<Member> members = memberIds.stream().map(memberService::findMemberById).collect(Collectors.toList());
        memberService.updateStatus(memberStatus, generation, memberStatusUpdateRequest.getPlatform(), members);
    }

    @Transactional
    public void transfer(final MemberTransferRequest request) {

        final Generation oldGeneration = generationService.getByNumberOrThrow(request.getOldGenerationNumber());
        final Generation newGeneration = generationService.getByNumberOrThrow(request.getNewGenerationNumber());
        final List<Member> members = memberService.findAllByMemberIds(request.getMemberIds());

        memberService.transfer(oldGeneration, newGeneration, members);
    }

    @Transactional
    public void dropOut(final MemberDropOutRequest request) {
        final Generation generation = generationService.getByNumberOrThrow(request.getGenerationNumber());
        final List<Member> members = memberService.findAllByMemberIds(request.getMemberIds());

        memberService.dropOut(generation, members);
    }
}
