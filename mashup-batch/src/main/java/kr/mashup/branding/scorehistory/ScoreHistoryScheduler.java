package kr.mashup.branding.scorehistory;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScoreHistoryScheduler {

	private final JobLauncher jobLauncher;
	private final ScoreHistoryConfig scoreHistoryConfig;

	@Scheduled(cron = "* * * * 0")
	public void runJob() {
		JobParameters jobParameters = new JobParametersBuilder()
			.addDate("date", new Date())
			.toJobParameters();

		try {
			jobLauncher.run(scoreHistoryConfig.scoreHistoryJob(), jobParameters);
		}catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException | JobParametersInvalidException | JobRestartException e){
			log.error("[ScoreHistory Batch Error] date = {}, message = {}", LocalDate.now(), e.getMessage());
		}
	}
}
