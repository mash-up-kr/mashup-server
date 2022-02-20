package kr.mashup.branding.signup;

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

import kr.mashup.branding.config.BatchConfig;
import kr.mashup.branding.domain.adminmember.AdminMemberService;
import lombok.RequiredArgsConstructor;

@ConditionalOnProperty(
    value = BatchConfig.SPRING_BATCH_JOB_NAMES,
    havingValue = AdminMemberSignupJobConfig.JOB_NAME
)
@Configuration
@RequiredArgsConstructor
public class AdminMemberSignupJobConfig {
    static final String JOB_NAME = "admin-member-sign-up";
    private static final String STEP_NAME = JOB_NAME + "-step";

    private final JobBuilderFactory jobBuilderFactory;
    private final JobRepository jobRepository;
    private final StepBuilderFactory stepBuilderFactory;
    private final AdminMemberService adminMemberService;

    @Bean
    public Job adminMemberSignUpJob() {
        return jobBuilderFactory.get(JOB_NAME)
            .repository(jobRepository)
            .start(adminMemberSignUpStep())
            .build();
    }

    @Bean
    @JobScope
    public Step adminMemberSignUpStep() {
        return stepBuilderFactory.get(STEP_NAME)
            .tasklet(adminMemberSignUpTasklet())
            .transactionManager(new ResourcelessTransactionManager())
            .build();
    }

    @Bean
    @StepScope
    public Tasklet adminMemberSignUpTasklet() {
        return new AdminMemberSignupTasklet(adminMemberService);
    }
}
