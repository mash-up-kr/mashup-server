package kr.mashup.branding.job.interview;

import kr.mashup.branding.config.BatchConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@ConditionalOnProperty(
    value = BatchConfig.SPRING_BATCH_JOB_NAMES,
    havingValue = InterviewSettingJobConfig.JOB_NAME
)
@Configuration
@RequiredArgsConstructor
public class InterviewSettingJobConfig {

    static final String JOB_NAME = "setting-interview-url";
    static final String STEP_NAME = JOB_NAME + "-step";

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final InterviewSettingTasklet interviewSettingTasklet;

    @Bean
    public Job setInterviewUrlJob() {
        return jobBuilderFactory.get(JOB_NAME)
            .incrementer(new RunIdIncrementer())
            .start(setInterviewUrlStep())
            .build();
    }

    @Bean
    @JobScope
    public Step setInterviewUrlStep() {
        return stepBuilderFactory.get(STEP_NAME)
            .tasklet(interviewSettingTasklet)
            .transactionManager(new ResourcelessTransactionManager())
            .build();
    }

}
