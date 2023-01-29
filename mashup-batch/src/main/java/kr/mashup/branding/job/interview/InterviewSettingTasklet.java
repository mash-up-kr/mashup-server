package kr.mashup.branding.job.interview;

import kr.mashup.branding.facade.InterviewGuideLinkFacadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@StepScope
@Component
@RequiredArgsConstructor
public class InterviewSettingTasklet implements Tasklet {

    private final InterviewGuideLinkFacadeService interviewGuideLinkFacadeService;
    @Value("#{jobParameters['platform']}")
    private String platformStr;
    @Value("#{jobParameters['generationNum']}")
    private Integer generationNum;
    @Value("#{jobParameters['link']}")
    private String link;

    @Override
    public RepeatStatus execute(
        StepContribution contribution,
        ChunkContext chunkContext
    ) {
        interviewGuideLinkFacadeService.setLink(
            platformStr,
            generationNum,
            link
        );
        return RepeatStatus.FINISHED;
    }
}
