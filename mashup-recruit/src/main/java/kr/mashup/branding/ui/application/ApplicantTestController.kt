package kr.mashup.branding.ui.application

import kr.mashup.branding.facade.application.ApplicationFacadeService
import kr.mashup.branding.service.application.ApplicationService
import kr.mashup.branding.ui.ApiResponse
import kr.mashup.branding.ui.application.vo.ApplicationResponse
import kr.mashup.branding.ui.application.vo.UpdateConfirmationRequest
import org.springframework.context.annotation.Profile
import org.springframework.web.bind.annotation.*
import springfox.documentation.annotations.ApiIgnore

@Profile("!production")
@RestController
@RequestMapping("/api/v1/test/applications")
class ApplicantTestController(
    private val applicationService: ApplicationService,
    private val applicationFacadeService: ApplicationFacadeService
) {

    /**
     * 개발서버에서 지원서 삭제
     */
    @DeleteMapping("/{applicationId}")
    fun delete(
        @PathVariable applicationId: Long?
    ): ApiResponse<*> {
        applicationService.delete(applicationId)
        return ApiResponse.success<Any>()
    }

    /**
     * 개발서버에서 지원자 응답 수정
     */
    @PostMapping("/{applicationId}/confirm")
    fun updateConfirmation(
        @ApiIgnore @ModelAttribute("applicantId") applicantId: Long?,
        @PathVariable applicationId: Long?,
        @RequestBody updateConfirmationRequest: UpdateConfirmationRequest?
    ): ApiResponse<ApplicationResponse> {
        val response = applicationFacadeService.updateConfirmForTest(applicantId, applicationId, updateConfirmationRequest)
        return ApiResponse.success(response)
    }
}