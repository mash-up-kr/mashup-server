package kr.mashup.branding.facade.scorehistory;

import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.pushnoti.vo.AttendanceScoreUpdatedVo;
import kr.mashup.branding.domain.scorehistory.ScoreHistory;
import kr.mashup.branding.domain.scorehistory.ScoreType;
import kr.mashup.branding.infrastructure.pushnoti.PushNotiEventPublisher;
import kr.mashup.branding.service.generation.GenerationService;
import kr.mashup.branding.service.member.MemberService;
import kr.mashup.branding.service.scorehistory.ScoreHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ScoreHistoryFacadeService {

    private final ScoreHistoryService scoreHistoryService;
    private final MemberService memberService;
    private final GenerationService generationService;
    private final PushNotiEventPublisher pushNotiEventPublisher;

    @Transactional
    public void addScore(Long memberId, Integer generationNumber, ScoreType scoreType, String name, LocalDate date, String memo) {

        final Generation generation = generationService.getByNumberOrThrow(generationNumber);
        final Member member = memberService.getActiveOrThrowById(memberId);

        final ScoreHistory scoreHistory = ScoreHistory.of(scoreType, member, LocalDateTime.of(date, LocalTime.MIN), name, generation, memo);

        scoreHistoryService.save(scoreHistory);
        pushNotiEventPublisher.publishPushNotiSendEvent(
            new AttendanceScoreUpdatedVo(List.of(member))
        );
    }
    @Transactional
    public void cancelScore(Long scoreHistoryId, String memo) {

        final ScoreHistory scoreHistory = scoreHistoryService.getByIdOrThrow(scoreHistoryId);

        scoreHistory.cancel(memo);
        pushNotiEventPublisher.publishPushNotiSendEvent(
            new AttendanceScoreUpdatedVo(List.of(scoreHistory.getMember()))
        );
    }

}
