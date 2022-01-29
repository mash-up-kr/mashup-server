package kr.mashup.branding.ui.application;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import kr.mashup.branding.domain.application.AnswerRequestVo;
import kr.mashup.branding.domain.application.Application;
import kr.mashup.branding.domain.application.CreateApplicationVo;
import kr.mashup.branding.domain.application.UpdateApplicationVo;
import kr.mashup.branding.facade.application.ApplicationFacadeService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/applications")
@RequiredArgsConstructor
public class ApplicationController {
    private final ApplicationFacadeService applicationFacadeService;

    /**
     * 팀 id(or name) 받아서 만들기
     * 새로만들면 201, 있던거면 200
     */
    @ApiOperation("내 지원서 생성")
    @PostMapping
    public ApplicationResponse create(
        @RequestBody CreateApplicationRequest createApplicationRequest
    ) {
        Application application = applicationFacadeService.create(toCreateApplicationVo(createApplicationRequest));
        return toApplicationResponse(application);
    }

    private CreateApplicationVo toCreateApplicationVo(CreateApplicationRequest createApplicationRequest) {
        Assert.notNull(createApplicationRequest, "'createApplicationRequest' must not be null");
        return new CreateApplicationVo(createApplicationRequest.getTeamId());
    }

    private ApplicationResponse toApplicationResponse(Application application) {
        Assert.notNull(application, "'application' must not be null");
        return new ApplicationResponse(application.getApplicationId());
    }

    /**
     * 임시저장
     * TODO: 이미 저장된 다른팀 지원서가 있으면 삭제
     */
    @ApiOperation("내 지원서 임시 저장")
    @PutMapping("/{applicationId}")
    public ApplicationResponse update(
        @PathVariable Long applicationId,
        @RequestBody UpdateApplicationRequest updateApplicationRequest
    ) {
        Application application = applicationFacadeService.update(
            applicationId,
            toUpdateApplicationVo(updateApplicationRequest)
        );
        return toApplicationResponse(application);
    }

    private UpdateApplicationVo toUpdateApplicationVo(UpdateApplicationRequest updateApplicationRequest) {
        Assert.notNull(updateApplicationRequest, "'updateApplicationRequest' must not be null");
        return UpdateApplicationVo.of(
            updateApplicationRequest.getAnswers()
                .stream()
                .map(this::toAnswerRequestVo)
                .collect(Collectors.toList())
        );
    }

    private AnswerRequestVo toAnswerRequestVo(AnswerRequest answerRequest) {
        Assert.notNull(answerRequest, "'answerRequest' must not be null");
        return AnswerRequestVo.of(
            answerRequest.getQuestionId(),
            answerRequest.getContent()
        );
    }

    /**
     * 지원서가 없으면? 오류
     * 임시저장이면 성공
     * 이미 제출했어도 성공
     */
    @ApiOperation("내 지원서 제출")
    @PostMapping("/{applicationId}/submit")
    public ApplicationResponse submit(
        @PathVariable Long applicationId
    ) {
        Application application = applicationFacadeService.submit(applicationId);
        return toApplicationResponse(application);
    }

    @ApiOperation("내 지원서 목록 조회")
    @GetMapping
    public List<ApplicationResponse> getApplications() {
        // TODO: applicant
        Long applicantId = 0L;
        List<Application> applications = applicationFacadeService.getApplications(applicantId);
        return applications.stream()
            .map(this::toApplicationResponse)
            .collect(Collectors.toList());
    }

    @ApiOperation("내 지원서 상세 조회")
    @GetMapping("/{applicationId}")
    public ApplicationResponse getApplication(
        @PathVariable Long applicationId
    ) {
        // TODO: applicant
        Long applicantId = 0L;
        Application application = applicationFacadeService.getApplication(applicantId, applicationId);
        return null;
    }
}
