package kr.mashup.branding.facade.scorehistory;

import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.scorehistory.ScoreHistory;
import kr.mashup.branding.service.member.MemberService;
import kr.mashup.branding.service.scorehistory.ScoreHistoryService;
import kr.mashup.branding.ui.scorehistory.response.ScoreHistoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScoreHistoryFacadeService {

    private final ScoreHistoryService scoreHistoryService;
    private final MemberService memberService;

    @Transactional(readOnly = true)
    public List<ScoreHistoryResponse> getScoreHistory(Long memberId) {
        final List<ScoreHistoryResponse> scoreHistoryResponses = new ArrayList<>();
        final Member member = memberService.getActiveOrThrowById(memberId);

        member.getMemberGenerations()
                .forEach(memberGeneration -> {
                    List<ScoreHistory> scoreHistories = scoreHistoryService
                        .getByMemberAndGeneration(member, memberGeneration.getGeneration())
                        .stream()
                        .filter(scoreHistory -> !scoreHistory.isCanceled())
                        .collect(Collectors.toList());
                    scoreHistoryResponses.add(createScoreHistory(scoreHistories, memberGeneration.getGeneration().getNumber()));
                });

        Collections.reverse(scoreHistoryResponses);

        return scoreHistoryResponses;
    }

    private ScoreHistoryResponse createScoreHistory(List<ScoreHistory> scoreHistories, int generationNumber) {

        final AtomicReference<Double> totalScore
                = new AtomicReference<>(0.0);

        final List<ScoreHistoryResponse.ScoreDetail> scoreDetails = new ArrayList<>();

        scoreHistories.forEach(scoreHistory ->
                scoreDetails.add(
                        ScoreHistoryResponse.ScoreDetail.of(
                                scoreHistory.getType(),
                                scoreHistory.getName(),
                                scoreHistory.getScore(),
                                totalScore.updateAndGet(v -> v + scoreHistory.getScore()),
                                scoreHistory.getDate(),
                                scoreHistory.getScheduleName(),
                                scoreHistory.isCanceled()
                        )
                ));
        Collections.reverse(scoreDetails);

        return ScoreHistoryResponse.of(generationNumber, totalScore.get(), scoreDetails);
    }
}
