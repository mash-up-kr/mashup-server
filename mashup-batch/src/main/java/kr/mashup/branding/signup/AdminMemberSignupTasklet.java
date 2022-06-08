package kr.mashup.branding.signup;

import java.util.stream.Collectors;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;

import kr.mashup.branding.domain.adminmember.AdminMember;
import kr.mashup.branding.service.adminmember.AdminMemberService;
import kr.mashup.branding.domain.adminmember.AdminMemberVo;
import kr.mashup.branding.domain.adminmember.Position;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class AdminMemberSignupTasklet implements Tasklet {
    private final AdminMemberService adminMemberService;

    @Value("#{jobParameters['username']}")
    private String username;
    @Value("#{jobParameters['password']}")
    private String password;
    @Value("#{jobParameters['phoneNumber']}")
    private String phoneNumber;
    @Value("#{jobParameters['position']}")
    private String position;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        String formattedJobParameters = chunkContext.getStepContext().getJobParameters().entrySet().stream()
            .map(it -> it.getKey() + ": " + it.getValue())
            .collect(Collectors.joining(", "));
        log.info("jobParameters: {}", formattedJobParameters);

        Assert.hasText(username, "'password' must not be null, empty or blank");
        Assert.hasText(password, "'password' must not be null, empty or blank");
        Assert.hasText(phoneNumber, "'phoneNumber' must not be null, empty or blank");
        Assert.hasText(position, "'position' must not be null, empty or blank");

        AdminMemberVo adminMemberVo = AdminMemberVo.of(
            username,
            password,
            phoneNumber,
            Position.valueOf(position)
        );
        log.info("adminMemberVo: {}", adminMemberVo);
        AdminMember adminMember = adminMemberService.signUp(adminMemberVo);
        log.info("adminMember: {}", adminMember);
        return RepeatStatus.FINISHED;
    }
}
