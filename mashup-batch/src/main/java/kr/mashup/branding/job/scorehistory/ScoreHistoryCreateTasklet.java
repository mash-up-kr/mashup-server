package kr.mashup.branding.job.scorehistory;

import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.pushnoti.vo.SeminarAttendanceAppliedVo;
import kr.mashup.branding.facade.ScoreHistoryFacadeService;
import kr.mashup.branding.infrastructure.pushnoti.PushNotiEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class ScoreHistoryCreateTasklet implements Tasklet {

    private final ScoreHistoryFacadeService scoreHistoryFacadeService;
    private final PushNotiEventPublisher pushNotiEventPublisher;

    @Override
    @Transactional
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
        List<Member> pushNotiTargetMembers = scoreHistoryFacadeService.calculate();
        pushNotiEventPublisher.publishPushNotiSendEvent(new SeminarAttendanceAppliedVo(pushNotiTargetMembers));
        return RepeatStatus.FINISHED;
    }
}
