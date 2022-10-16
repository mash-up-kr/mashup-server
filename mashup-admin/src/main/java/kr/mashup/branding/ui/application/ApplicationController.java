package kr.mashup.branding.ui.application;

import kr.mashup.branding.domain.application.ApplicationQueryRequest;
import kr.mashup.branding.domain.application.confirmation.ApplicantConfirmationStatus;
import kr.mashup.branding.domain.application.result.UpdateApplicationResultVo;
import kr.mashup.branding.facade.application.AdminApplicationFacadeService;
import kr.mashup.branding.ui.ApiResponse;
import kr.mashup.branding.ui.application.vo.ApplicationDetailResponse;
import kr.mashup.branding.ui.application.vo.ApplicationResultStatus;
import kr.mashup.branding.ui.application.vo.ApplicationSimpleResponse;
import kr.mashup.branding.ui.application.vo.UpdateApplicationResultRequest;
import kr.mashup.branding.ui.application.vo.UpdateApplicationResultsRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@RequestMapping("/api/v1/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final AdminApplicationFacadeService adminApplicationFacadeService;
    private final ApplicationAssembler applicationAssembler;

    @GetMapping
    public ApiResponse<List<ApplicationSimpleResponse>> getApplications(
        @ApiIgnore @ModelAttribute("adminMemberId") Long adminMemberId,
        @RequestParam(defaultValue = "12") Integer generationNumber,
        @RequestParam(required = false) String searchWord,
        @RequestParam(required = false) Long teamId,
        @RequestParam(required = false) ApplicantConfirmationStatus confirmStatus,
        @RequestParam(required = false) ApplicationResultStatus resultStatus,
        @RequestParam(required = false) Boolean isShowAll,
        Pageable pageable
    ) {

        final ApplicationQueryRequest queryRequest
            = applicationAssembler.toApplicationQueryRequest(
                generationNumber, searchWord, teamId, confirmStatus, resultStatus, isShowAll, pageable);

        final Page<ApplicationSimpleResponse> responses
            = adminApplicationFacadeService.getApplications(adminMemberId, queryRequest);

        return ApiResponse.success(responses);
    }

    @GetMapping("/{applicationId}")
    public ApiResponse<ApplicationDetailResponse> getApplication(
        @ApiIgnore @ModelAttribute("adminMemberId") Long adminMemberId,
        @PathVariable Long applicationId
    ) {
        final ApplicationDetailResponse response
            = adminApplicationFacadeService.getApplicationDetail(adminMemberId, applicationId);

        return ApiResponse.success(response);
    }

    /**
     * 여러개의 지원서에 대해서 결과 변경
     */
    @PostMapping("/update-result")
    public ApiResponse<List<ApplicationSimpleResponse>> updateResult(
        @ApiIgnore @ModelAttribute("adminMemberId") Long adminMemberId,
        @RequestBody UpdateApplicationResultsRequest updateApplicationResultsRequest
    ) {

        List<ApplicationSimpleResponse> responses = adminApplicationFacadeService.updateResults(
            adminMemberId,
            applicationAssembler.toUpdateApplicationResultsVoList(updateApplicationResultsRequest)
        );

        return ApiResponse.success(responses);
    }

    /**
     * 지원서의 결과 및 면접일정 변경
     */
    @PostMapping("/{applicationId}/update-result")
    public ApiResponse<ApplicationSimpleResponse> updateResult(
        @ApiIgnore @ModelAttribute("adminMemberId") Long adminMemberId,
        @PathVariable Long applicationId,
        @RequestBody UpdateApplicationResultRequest updateApplicationResultRequest
    ) {
        final UpdateApplicationResultVo updateVo = updateApplicationResultRequest.toVo(applicationId);

        final ApplicationSimpleResponse response
            = adminApplicationFacadeService.updateResult(adminMemberId, updateVo);

        return ApiResponse.success(response);
    }

}
