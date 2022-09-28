package kr.mashup.branding.facade.member;

import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.member.Platform;
import kr.mashup.branding.domain.scorehistory.ScoreHistory;
import kr.mashup.branding.domain.scorehistory.ScoreType;
import kr.mashup.branding.service.generation.GenerationService;
import kr.mashup.branding.service.member.MemberService;
import kr.mashup.branding.service.scorehistory.ScoreHistoryService;
import kr.mashup.branding.ui.member.response.MemberDetailResponse;
import kr.mashup.branding.ui.member.response.MemberResponse;
import kr.mashup.branding.ui.scorehistory.response.ScoreHistoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneOffset;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
public class MemberFacadeService {

    private final MemberService memberService;
    private final GenerationService generationService;
    private final ScoreHistoryService scoreHistoryService;

    @Transactional(readOnly = true)
    public Page<MemberResponse> getAllActive(Integer generationNumber, Pageable pageable) {
        Generation generation = generationService.getByNumberOrThrow(generationNumber);
        return memberService
            .getActiveAllByGeneration(generation, pageable)
            .map(member -> {
                double score = scoreHistoryService
                    .getByMemberAndGeneration(member, generation)
                    .stream()
                    .map(ScoreHistory::getScore)
                    .reduce(0d, Double::sum);
                return MemberResponse.of(member.getId(), member.getName(), member.getIdentification(), memberService.getLatestPlatform(member).name(), score);
            });
    }
    @Transactional(readOnly = true)
    public Page<MemberResponse> getAllActiveByPlatform(Integer generationNumber, Platform platform, Pageable pageable) {
        Generation generation = generationService.getByNumberOrThrow(generationNumber);

        return memberService
            .getAllByPlatformAndGeneration(platform, generation, pageable)
            .map(member -> {
                double score = scoreHistoryService
                    .getByMemberAndGeneration(member, generation)
                    .stream()
                    .map(ScoreHistory::getScore)
                    .reduce(0d, Double::sum);
                Platform latestPlatform = memberService.getLatestPlatform(member);
                return MemberResponse.of(member.getId(), member.getName(), member.getIdentification(), latestPlatform.name(), score);
            });
    }

    @Transactional(readOnly = true)
    public MemberDetailResponse getAttendance(Integer generationNumber, Long memberId) {
        // 이름 아이디 기수 플랫폼, 총활동점수
        // 활동점수 히스토리
        // 제목, 세미나정보, 등록일시, 점수, 총활동점수,
        final Generation generation = generationService.getByNumberOrThrow(generationNumber);
        final Member member = memberService.getActiveOrThrowById(memberId);
        final String memberName = member.getName();
        final String identification = member.getIdentification();
        final Platform platform = memberService.getPlatform(member, generation);
        AtomicReference<Double> accumulatedScore = new AtomicReference<>(0.0);

        List<ScoreHistoryResponse> scoreHistories = scoreHistoryService
            .getByMemberAndGeneration(member, generation)
            .stream()
            .sorted(Comparator.comparingLong(it -> it.getDate().toInstant(ZoneOffset.UTC).getEpochSecond()))
            .map(it ->{
                if(!it.isCanceled()){
                    accumulatedScore.updateAndGet(v->v+it.getScore());
                }
                return ScoreHistoryResponse.from(it, accumulatedScore.get());
            })
            .collect(Collectors.toList());
        Collections.reverse(scoreHistories);

        return MemberDetailResponse.of(memberName, identification, generationNumber, platform.name(), scoreHistories);
    }

}
