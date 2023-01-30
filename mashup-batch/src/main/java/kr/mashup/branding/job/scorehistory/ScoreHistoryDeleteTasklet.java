package kr.mashup.branding.job.scorehistory;

import kr.mashup.branding.facade.ScoreHistoryFacadeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
public class ScoreHistoryDeleteTasklet implements Tasklet {

    @Value("#{jobParameters['scheduleStartDate']}")
    private String scheduleStartDate;

    private final ScoreHistoryFacadeService scoreHistoryFacadeService;

    @Override
    @Transactional
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
        scoreHistoryFacadeService.delete(scheduleStartDate);
        return RepeatStatus.FINISHED;
    }
}
