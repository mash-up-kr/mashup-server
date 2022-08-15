package kr.mashup.branding.ui.application;

import kr.mashup.branding.domain.application.Application;
import kr.mashup.branding.service.application.ApplicationService;
import kr.mashup.branding.facade.application.ApplicationFacadeService;
import kr.mashup.branding.ui.ApiResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Profile("!production")
@RestController
@RequestMapping("/api/v1/test/applications")
@RequiredArgsConstructor
public class TestController {
    private final ApplicationService applicationService;
    private final ApplicationFacadeService applicationFacadeService;
    private final ApplicationAssembler applicationAssembler;

    /**
     * 개발서버에서 지원서 삭제
     */
    @DeleteMapping("/{applicationId}")
    public ApiResponse<?> delete(
        @PathVariable Long applicationId
    ) {
        applicationService.delete(applicationId);
        return ApiResponse.success();
    }

    /**
     * 개발서버에서 지원자 응답 수정
     */
    @PostMapping("/{applicationId}/confirm")
    public ApiResponse<ApplicationResponse> updateConfirmation(
        @ApiIgnore @ModelAttribute("applicantId") Long applicantId,
        @PathVariable Long applicationId,
        @RequestBody UpdateConfirmationRequest updateConfirmationRequest
    ) {
        Application application = applicationFacadeService
            .updateConfirmForTest(applicantId, applicationId, updateConfirmationRequest);
        return ApiResponse.success(applicationAssembler.toApplicationResponse(application));
    }
}
