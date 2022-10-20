package kr.mashup.branding.scorehistory;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import kr.mashup.branding.repository.schedule.ScheduleRepository;
import kr.mashup.branding.service.attendance.AttendanceService;
import kr.mashup.branding.service.scorehistory.ScoreHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ScoreHistoryConfig {
    static final String JOB_NAME = "score-history-create";
    private static final String STEP_NAME = JOB_NAME + "-step";

    private final JobBuilderFactory jobBuilderFactory;
    private final JobRepository jobRepository;
    private final StepBuilderFactory stepBuilderFactory;
    private final ScheduleRepository scheduleRepository;
    private final AttendanceService attendanceService;
    private final ScoreHistoryService scoreHistoryService;

    @Bean
    public Job scoreHistoryJob() {
        return jobBuilderFactory.get(JOB_NAME)
            .repository(jobRepository)
            .start(scoreHistoryStep())
            .build();
    }

    @Bean
    @JobScope
    public Step scoreHistoryStep() {
        return stepBuilderFactory.get(STEP_NAME)
            .tasklet(scoreHistoryTasklet(scheduleRepository, attendanceService, scoreHistoryService))
            .transactionManager(new ResourcelessTransactionManager())
            .build();
    }

    @Bean
    @StepScope
    public Tasklet scoreHistoryTasklet(ScheduleRepository scheduleRepository, AttendanceService attendanceService, ScoreHistoryService scoreHistoryService) {
        return new ScoreHistoryTasklet(scheduleRepository, attendanceService, scoreHistoryService);
    }
}
