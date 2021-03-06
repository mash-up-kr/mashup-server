package kr.mashup.branding.ui.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import kr.mashup.branding.domain.application.Application;
import kr.mashup.branding.facade.application.ApplicationFacadeService;
import kr.mashup.branding.ui.ApiResponse;
import lombok.RequiredArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/v1/applications")
@RequiredArgsConstructor
public class ApplicationController {
    private final ApplicationFacadeService applicationFacadeService;
    private final ApplicationAssembler applicationAssembler;

    /**
     * 팀 id(or name) 받아서 만들기
     */
    @ApiOperation("내 지원서 생성")
    @PostMapping
    public ApiResponse<ApplicationResponse> create(
        @ApiIgnore @ModelAttribute("applicantId") Long applicantId,
        @RequestBody CreateApplicationRequest createApplicationRequest
    ) {
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
     */
    @ApiOperation("내 지원서 임시 저장")
    @PutMapping("/{applicationId}")
    public ApiResponse<ApplicationResponse> update(
        @ApiIgnore @ModelAttribute("applicantId") Long applicantId,
        @PathVariable Long applicationId,
        @RequestBody UpdateApplicationRequest updateApplicationRequest
    ) {
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
        @ApiIgnore @ModelAttribute("applicantId") Long applicantId,
        @PathVariable Long applicationId,
        @RequestBody ApplicationSubmitRequest applicationSubmitRequest
    ) {
        Application application = applicationFacadeService.submit(
            applicantId,
            applicationId,
            applicationAssembler.toApplicationSubmitRequestVo(applicationSubmitRequest)
        );
        return ApiResponse.success(
            applicationAssembler.toApplicationResponse(application)
        );
    }

    @ApiOperation("내 지원서 목록 조회")
    @GetMapping
    public ApiResponse<List<ApplicationResponse>> getApplications(
        @ApiIgnore @ModelAttribute("applicantId") Long applicantId
    ) {
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
        @ApiIgnore @ModelAttribute("applicantId") Long applicantId,
        @PathVariable Long applicationId
    ) {
        Application application = applicationFacadeService.getApplication(applicantId, applicationId);
        return ApiResponse.success(
            applicationAssembler.toApplicationResponse(application)
        );
    }

    @ApiOperation("지원자 응답")
    @PostMapping("/{applicationId}/confirm")
    public ApiResponse<ApplicationResponse> updateConfirmation(
        @ApiIgnore @ModelAttribute("applicantId") Long applicantId,
        @PathVariable Long applicationId,
        @RequestBody UpdateConfirmationRequest updateConfirmationRequest
    ) {
        Application application = applicationFacadeService
            .updateConfirm(applicantId, applicationId, updateConfirmationRequest);
        return ApiResponse.success(applicationAssembler.toApplicationResponse(application));
    }
}
