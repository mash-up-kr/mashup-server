package kr.mashup.branding.ui.application;

import io.swagger.annotations.ApiOperation;
import kr.mashup.branding.facade.application.ApplicationFacadeService;
import kr.mashup.branding.ui.ApiResponse;
import kr.mashup.branding.ui.application.vo.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@RequestMapping("/api/v1/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationFacadeService applicationFacadeService;

    @ApiOperation("지원서 목록 조회")
    @GetMapping("/generationNumber")
    public ApiResponse<List<ApplicationFormResponse>> getApplications(
        @PathVariable Integer generationNumber
    ){
        final List<ApplicationFormResponse> response
            = applicationFacadeService.getApplicationForms(generationNumber);

        return ApiResponse.success(response);
    }


    /**
     * 팀 id(or name) 받아서 만들기
     */
    @ApiOperation("내 지원서 생성")
    @PostMapping
    public ApiResponse<ApplicationResponse> create(
        @ApiIgnore @ModelAttribute("applicantId") Long applicantId,
        @RequestBody CreateApplicationRequest request
    ) {
        final ApplicationResponse response
            = applicationFacadeService.create(applicantId, request.getTeamId());

        return ApiResponse.success(response);
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
        final ApplicationResponse response
            = applicationFacadeService.update(applicantId, applicationId, updateApplicationRequest.toVo());

        return ApiResponse.success(response);
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
        final ApplicationResponse response
            = applicationFacadeService.submit(applicantId, applicationId, applicationSubmitRequest.toVo());

        return ApiResponse.success(response);
    }

    @ApiOperation("내 지원서 목록 조회")
    @GetMapping
    public ApiResponse<List<ApplicationResponse>> getApplications(
        @ApiIgnore @ModelAttribute("applicantId") Long applicantId
    ) {
        final List<ApplicationResponse> responses
            = applicationFacadeService.getApplications(applicantId);

        return ApiResponse.success(responses);
    }

    @ApiOperation("내 지원서 상세 조회")
    @GetMapping("/{applicationId}")
    public ApiResponse<ApplicationResponse> getApplication(
        @ApiIgnore @ModelAttribute("applicantId") Long applicantId,
        @PathVariable Long applicationId
    ) {
        final ApplicationResponse response
            = applicationFacadeService.getApplication(applicantId, applicationId);

        return ApiResponse.success(response);
    }

    @ApiOperation("지원자 응답")
    @PostMapping("/{applicationId}/confirm")
    public ApiResponse<ApplicationResponse> updateConfirmation(
        @ApiIgnore @ModelAttribute("applicantId") Long applicantId,
        @PathVariable Long applicationId,
        @RequestBody UpdateConfirmationRequest updateConfirmationRequest
    ) {
        final ApplicationResponse response
            = applicationFacadeService.updateConfirm(applicantId, applicationId, updateConfirmationRequest);

        return ApiResponse.success(response);
    }

    @ApiOperation("기수 별 스케줄 조회")
    @GetMapping("/schedule/{generationNumber")
    public ApiResponse<List<RecruitScheduleResponse>> getRecruitSchedule(
        @PathVariable Long generationNumber
    ){
        final List<RecruitScheduleResponse> response = applicationFacadeService.getRecruitSchedule(generationNumber);

        return ApiResponse.success(response);
    }
}
