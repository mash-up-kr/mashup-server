package kr.mashup.branding.facade.scorehistory;

import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.scorehistory.ScoreHistory;
import kr.mashup.branding.service.generation.GenerationService;
import kr.mashup.branding.service.member.MemberService;
import kr.mashup.branding.service.scorehistory.ScoreHistoryService;
import kr.mashup.branding.ui.scorehistory.response.ScoreHistoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class ScoreHistoryFacadeService {

    private final ScoreHistoryService scoreHistoryService;
    private final MemberService memberService;
    private final GenerationService generationService;

    @Transactional(readOnly = true)
    public List<ScoreHistoryResponse> getScoreHistory(Long memberId) {
        List<ScoreHistoryResponse> scoreHistoryResponses = new ArrayList<>();
        Member member = memberService.getOrThrowById(memberId);

        generationService.getAll().stream()
            .filter(generation -> generation.getNumber() >= member.getGeneration().getNumber())
            .forEach(generation -> {
                    List<ScoreHistory> scoreHistories = scoreHistoryService.getByMemberAndGeneration(member, generation);
                    if (scoreHistories.isEmpty()) {
                        return;
                    }
                    scoreHistoryResponses.add(createScoreHistory(scoreHistories, generation.getNumber()));
                }
            );

        return scoreHistoryResponses;
    }

    private ScoreHistoryResponse createScoreHistory(List<ScoreHistory> scoreHistories, int generationNumber) {
        AtomicReference<Double> totalScore = new AtomicReference<>(0.0);
        List<ScoreHistoryResponse.ScoreDetail> scoreDetails = new ArrayList<>();

        scoreHistories.forEach(scoreHistory ->
            scoreDetails.add(
                ScoreHistoryResponse.ScoreDetail.of(
                    scoreHistory.getName(),
                    scoreHistory.getScore(),
                    totalScore.updateAndGet(v -> v + scoreHistory.getScore()),
                    scoreHistory.getDate(),
                    scoreHistory.getScheduleName()
                )
            ));

        return ScoreHistoryResponse.of(generationNumber, totalScore.get(), scoreDetails);
    }
}
