package kr.mashup.branding.facade.scorehistory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.scorehistory.ScoreHistory;
import kr.mashup.branding.service.generation.GenerationService;
import kr.mashup.branding.service.member.MemberService;
import kr.mashup.branding.service.scorehistory.ScoreHistoryService;
import kr.mashup.branding.ui.scorehistory.request.ScoreHistoryCreateRequest;
import kr.mashup.branding.ui.scorehistory.response.ScoreHistoryResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScoreHistoryFacadeService {

    private final ScoreHistoryService scoreHistoryService;
    private final MemberService memberService;
    private final GenerationService generationService;

    @Transactional
    public ScoreHistoryResponse save(ScoreHistoryCreateRequest req) {
        Generation generation =
            generationService.getByIdOrThrow(req.getGenerationId());
        Member member =
            memberService.getOrThrowById(req.getMemberId());

        ScoreHistory scoreHistory = scoreHistoryService.save(
            ScoreHistory.of(req.getScoreType(), req.getScheduleName(), req.getDate(), generation, member)
        );

        return ScoreHistoryResponse.from(scoreHistory);
    }
}
