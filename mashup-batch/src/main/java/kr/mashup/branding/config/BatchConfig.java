package kr.mashup.branding.config;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.annotation.Configuration;

@EnableBatchProcessing
@Configuration
public class BatchConfig {
    public static final String SPRING_BATCH_JOB_NAMES = "spring.batch.job.names";
}
