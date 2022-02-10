package kr.mashup.branding.ui.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import kr.mashup.branding.domain.applicant.ApplicantService;
import kr.mashup.branding.domain.application.Application;
import kr.mashup.branding.facade.application.ApplicationFacadeService;
import kr.mashup.branding.ui.ApiResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/applications")
@RequiredArgsConstructor
public class ApplicationController {
    private final ApplicationFacadeService applicationFacadeService;
    private final ApplicationAssembler applicationAssembler;
    private final ApplicantService applicantService;

    /**
     * 팀 id(or name) 받아서 만들기
     */
    @ApiOperation("내 지원서 생성")
    @PostMapping
    public ApiResponse<ApplicationResponse> create(
        @RequestBody CreateApplicationRequest createApplicationRequest
    ) {
        Long applicantId = getTesterApplicantId();
        Application application = applicationFacadeService.create(
            applicantId,
            applicationAssembler.toCreateApplicationVo(createApplicationRequest)
        );
        return ApiResponse.success(
            applicationAssembler.toApplicationResponse(application)
        );
    }

    /**
     * 임시저장
     * TODO: 이미 저장된 다른팀 지원서가 있으면 삭제
     */
    @ApiOperation("내 지원서 임시 저장")
    @PutMapping("/{applicationId}")
    public ApiResponse<ApplicationResponse> update(
        @PathVariable Long applicationId,
        @RequestBody UpdateApplicationRequest updateApplicationRequest
    ) {
        Long applicantId = getTesterApplicantId();
        Application application = applicationFacadeService.update(
            applicantId,
            applicationId,
            applicationAssembler.toUpdateApplicationVo(updateApplicationRequest));
        return ApiResponse.success(
            applicationAssembler.toApplicationResponse(application)
        );
    }

    /**
     * 지원서가 없으면? 오류
     * 임시저장이면 성공
     * 이미 제출했어도 성공
     */
    @ApiOperation("내 지원서 제출")
    @PostMapping("/{applicationId}/submit")
    public ApiResponse<ApplicationResponse> submit(
        @PathVariable Long applicationId
    ) {
        Long applicantId = getTesterApplicantId();
        Application application = applicationFacadeService.submit(applicantId, applicationId);
        return ApiResponse.success(
            applicationAssembler.toApplicationResponse(application)
        );
    }

    @ApiOperation("내 지원서 목록 조회")
    @GetMapping
    public ApiResponse<List<ApplicationResponse>> getApplications() {
        Long applicantId = getTesterApplicantId();
        List<Application> applications = applicationFacadeService.getApplications(applicantId);
        return ApiResponse.success(
            applications.stream()
                .map(applicationAssembler::toApplicationResponse)
                .collect(Collectors.toList())
        );
    }

    @ApiOperation("내 지원서 상세 조회")
    @GetMapping("/{applicationId}")
    public ApiResponse<ApplicationResponse> getApplication(
        @PathVariable Long applicationId
    ) {
        Long applicantId = getTesterApplicantId();
        Application application = applicationFacadeService.getApplication(applicantId, applicationId);
        return ApiResponse.success(
            applicationAssembler.toApplicationResponse(application)
        );
    }

    // TODO: 로그인 완성되면 삭제해야함
    private Long getTesterApplicantId() {
        return applicantService.getTester().getApplicantId();
    }
}
