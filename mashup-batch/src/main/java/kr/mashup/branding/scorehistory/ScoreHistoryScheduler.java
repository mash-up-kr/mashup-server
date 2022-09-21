package kr.mashup.branding.scorehistory;

import java.time.LocalDate;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ScoreHistoryScheduler {

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private ScoreHistoryConfig scoreHistoryConfig;

	@Autowired
	private Step step;

	@Scheduled(cron = "0 5 * * 0")
	public void runJob() {
		JobParameters jobParameters = new JobParameters();

		try {
			jobLauncher.run(scoreHistoryConfig.job(step), jobParameters);
		}catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException | JobParametersInvalidException | JobRestartException e){
			log.error("[ScoreHistory Batch Error] date = {}, message = {}", LocalDate.now(), e.getMessage());
		}
	}
}
