package kr.mashup.branding.job.scorehistory;

import kr.mashup.branding.config.BatchConfig;
import kr.mashup.branding.facade.ScoreHistoryFacadeService;
import kr.mashup.branding.infrastructure.pushnoti.PushNotiEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@ConditionalOnProperty(
    value = BatchConfig.SPRING_BATCH_JOB_NAMES,
    havingValue = ScoreHistoryCreateConfig.JOB_NAME
)
@Configuration
@RequiredArgsConstructor
@Slf4j
public class ScoreHistoryCreateConfig {
    static final String JOB_NAME = "score-history-create";
    private static final String STEP_NAME = JOB_NAME + "-step";

    private final JobBuilderFactory jobBuilderFactory;
    private final JobRepository jobRepository;
    private final StepBuilderFactory stepBuilderFactory;
    private final ScoreHistoryFacadeService scoreHistoryFacadeService;
    private final PushNotiEventPublisher pushNotiEventPublisher;

    @Bean
    public Job CreateScoreHistoryJob() {
        return jobBuilderFactory.get(JOB_NAME)
            .repository(jobRepository)
            .start(CreateScoreHistoryStep())
            .build();
    }

    @Bean
    @JobScope
    public Step CreateScoreHistoryStep() {
        return stepBuilderFactory.get(STEP_NAME)
            .tasklet(CreateScoreHistoryTasklet(scoreHistoryFacadeService, pushNotiEventPublisher))
            .transactionManager(new ResourcelessTransactionManager())
            .build();
    }

    @Bean
    @StepScope
    public Tasklet CreateScoreHistoryTasklet(ScoreHistoryFacadeService scoreHistoryFacadeService, PushNotiEventPublisher pushNotiEventPublisher) {
        return new ScoreHistoryCreateTasklet(scoreHistoryFacadeService, pushNotiEventPublisher);
    }
}
