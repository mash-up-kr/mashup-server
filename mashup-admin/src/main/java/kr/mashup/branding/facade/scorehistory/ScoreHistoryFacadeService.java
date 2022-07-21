package kr.mashup.branding.facade.scorehistory;

import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.scorehistory.ScoreHistory;
import kr.mashup.branding.domain.scorehistory.ScoreType;
import kr.mashup.branding.service.generation.GenerationService;
import kr.mashup.branding.service.member.MemberService;
import kr.mashup.branding.service.scorehistory.ScoreHistoryService;
import kr.mashup.branding.ui.scorehistory.request.ScoreHistoryCreateRequest;
import kr.mashup.branding.ui.scorehistory.response.ScoreHistoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
@RequiredArgsConstructor
public class ScoreHistoryFacadeService {

    private final ScoreHistoryService scoreHistoryService;
    private final MemberService memberService;
    private final GenerationService generationService;

    @Transactional
    public ScoreHistoryResponse save(ScoreHistoryCreateRequest req) {
        String name = req.getScoreType().getName();
        Double score = req.getScoreType().getScore();

        if (req.getScoreType() == ScoreType.ETC) {
            Assert.notNull(req.getScoreName(), "For ETC type, 'scoreName' must not be null");
            Assert.notNull(req.getScore(), "For ETC type, 'score' must not be null");
            name = req.getScoreName();
            score = req.getScore();
        }

        Generation generation = generationService.getByIdOrThrow(req.getGenerationId());
        Member member = memberService.getOrThrowById(req.getMemberId());

        ScoreHistory scoreHistory = scoreHistoryService.save(
            ScoreHistory.of(name, score, req.getDate(), req.getScheduleName(), generation, member)
        );

        return ScoreHistoryResponse.from(scoreHistory);
    }
}
