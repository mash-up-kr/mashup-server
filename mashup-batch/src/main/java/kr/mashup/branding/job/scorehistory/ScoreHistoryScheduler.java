package kr.mashup.branding.job.scorehistory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@RequiredArgsConstructor
//@Component
public class ScoreHistoryScheduler {

    private final JobLauncher jobLauncher;
    private final ScoreHistoryConfig scoreHistoryConfig;

    // TODO: 젠킨스 파라미터로 스케줄링 시간 조정할 수 있도록 변경 필요(현재는 매주 토요일 오후 11시 배치 동작)
    @Scheduled(cron = "* * 23 * * 5")
    public void runJob() {
        JobParameters params = new JobParametersBuilder()
            .addString("datetime", LocalDateTime.now().toString())
            .toJobParameters();

        try {
            jobLauncher.run(scoreHistoryConfig.scoreHistoryJob(), params);
        } catch (JobExecutionAlreadyRunningException |
                 JobInstanceAlreadyCompleteException |
                 JobParametersInvalidException | JobRestartException e) {
            log.error("[ScoreHistory Batch Error] date = {}, message = {}", LocalDate.now(), e.getMessage());
        }
    }
}
