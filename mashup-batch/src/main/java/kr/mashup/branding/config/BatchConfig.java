package kr.mashup.branding.config;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableBatchProcessing
@EnableScheduling
@EnableWebMvc
@Configuration
public class BatchConfig {
    public static final String SPRING_BATCH_JOB_NAMES = "spring.batch.job.names";
}
