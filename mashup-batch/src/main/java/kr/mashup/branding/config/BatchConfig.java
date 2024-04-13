package kr.mashup.branding.config;

import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;

@EnableBatchProcessing
@EnableScheduling
@EnableWebMvc
@Configuration
public class BatchConfig extends DefaultBatchConfigurer {
    public static final String SPRING_BATCH_JOB_NAMES = "spring.batch.job.names";

    // spring batch 5 로 버전 업데이트시에 대응 필요
    @Override
    public void setDataSource(DataSource dataSource) {
    }
}
