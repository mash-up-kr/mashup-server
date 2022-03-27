package kr.mashup.branding.ui.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.mashup.branding.domain.application.Application;
import kr.mashup.branding.domain.application.confirmation.ApplicantConfirmationStatus;
import kr.mashup.branding.facade.application.ApplicationDetailVo;
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

    @GetMapping
    public ApiResponse<List<ApplicationSimpleResponse>> getApplications(
        @ApiIgnore @ModelAttribute("adminMemberId") Long adminMemberId,
        @RequestParam(required = false) String searchWord,
        @RequestParam(required = false) Long teamId,
        @RequestParam(required = false) ApplicantConfirmationStatus confirmStatus,
        @RequestParam(required = false) ApplicationResultStatus resultStatus,
        @RequestParam(required = false) Boolean isShowAll,
        Pageable pageable
    ) {
        return ApiResponse.success(
            applicationFacadeService.getApplications(
                adminMemberId,
                applicationAssembler.toApplicationQueryVo(
                    searchWord,
                    teamId,
                    confirmStatus,
                    resultStatus,
                    isShowAll,
                    pageable
                )
            ).map(applicationAssembler::toApplicationSimpleResponse)
        );
    }

    @GetMapping("/{applicationId}")
    public ApiResponse<ApplicationDetailResponse> getApplication(
        @ApiIgnore @ModelAttribute("adminMemberId") Long adminMemberId,
        @PathVariable Long applicationId
    ) {
        ApplicationDetailVo applicationDetailVo = applicationFacadeService.getApplicationDetail(adminMemberId, applicationId);
        return ApiResponse.success(
            applicationAssembler.toApplicationDetailResponse(applicationDetailVo)
        );
    }

    /**
     * 여러개의 지원서에 대해서 결과 변경
     */
    @PostMapping("/update-result")
    public ApiResponse<List<ApplicationSimpleResponse>> updateResult(
        @ApiIgnore @ModelAttribute("adminMemberId") Long adminMemberId,
        @RequestBody UpdateApplicationResultsRequest updateApplicationResultsRequest
    ) {
        return ApiResponse.success(
            applicationFacadeService.updateResults(
                adminMemberId,
                applicationAssembler.toUpdateApplicationResultsVoList(updateApplicationResultsRequest)
            ).stream()
                .map(applicationAssembler::toApplicationSimpleResponse)
                .collect(Collectors.toList())
        );
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
        Application application = applicationFacadeService.updateResult(
            adminMemberId,
            applicationAssembler.toUpdateApplicationResultVo(applicationId, updateApplicationResultRequest)
        );
        return ApiResponse.success(
            applicationAssembler.toApplicationSimpleResponse(application)
        );
    }
}
