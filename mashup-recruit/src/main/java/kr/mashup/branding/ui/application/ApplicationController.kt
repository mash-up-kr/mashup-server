package kr.mashup.branding.ui.application

import io.swagger.annotations.ApiOperation
import kr.mashup.branding.facade.application.ApplicationFacadeService
import kr.mashup.branding.ui.ApiResponse
import kr.mashup.branding.ui.application.vo.*
import org.springframework.web.bind.annotation.*
import springfox.documentation.annotations.ApiIgnore

@RestController
@RequestMapping("/api/v1/applications")
class ApplicationController(
    private val applicationFacadeService: ApplicationFacadeService
) {

    /**
     * 팀 id(or name) 받아서 만들기
     */
    @ApiOperation("내 지원서 생성")
    @PostMapping
    fun create(
        @ApiIgnore @ModelAttribute("applicantId") applicantId: Long,
        @RequestBody request: CreateApplicationRequest
    ): ApiResponse<ApplicationResponse> {
        val response = applicationFacadeService.create(applicantId, request.teamId)
        return ApiResponse.success(response)
    }

    /**
     * 임시저장
     */
    @ApiOperation("내 지원서 임시 저장")
    @PutMapping("/{applicationId}")
    fun update(
        @ApiIgnore @ModelAttribute("applicantId") applicantId: Long,
        @PathVariable applicationId: Long,
        @RequestBody updateApplicationRequest: UpdateApplicationRequest
    ): ApiResponse<ApplicationResponse> {
        val response = applicationFacadeService.update(applicantId, applicationId, updateApplicationRequest.toVo())
        return ApiResponse.success(response)
    }

    /**
     * 지원서가 없으면? 오류
     * 임시저장이면 성공
     * 이미 제출했어도 성공
     */
    @ApiOperation("내 지원서 제출")
    @PostMapping("/{applicationId}/submit")
    fun submit(
        @ApiIgnore @ModelAttribute("applicantId") applicantId: Long,
        @PathVariable applicationId: Long,
        @RequestBody applicationSubmitRequest: ApplicationSubmitRequest
    ): ApiResponse<ApplicationResponse> {
        val response = applicationFacadeService.submit(applicantId, applicationId, applicationSubmitRequest.toVo())
        return ApiResponse.success(response)
    }

    @ApiOperation("내 지원서 목록 조회")
    @GetMapping
    fun getApplications(
        @ApiIgnore @ModelAttribute("applicantId") applicantId: Long
    ): ApiResponse<List<ApplicationResponse>> {
        val responses = applicationFacadeService.getApplications(applicantId)
        return ApiResponse.success(responses)
    }

    @ApiOperation("내 지원서 상세 조회")
    @GetMapping("/{applicationId}")
    fun getApplication(
        @ApiIgnore @ModelAttribute("applicantId") applicantId: Long,
        @PathVariable applicationId: Long
    ): ApiResponse<ApplicationResponse> {
        val response = applicationFacadeService.getApplication(applicantId, applicationId)
        return ApiResponse.success(response)
    }

    @ApiOperation("지원자 응답")
    @PostMapping("/{applicationId}/confirm")
    fun updateConfirmation(
        @ApiIgnore @ModelAttribute("applicantId") applicantId: Long,
        @PathVariable applicationId: Long,
        @RequestBody updateConfirmationRequest: UpdateConfirmationRequest?
    ): ApiResponse<ApplicationResponse> {
        val response = applicationFacadeService.updateConfirm(applicantId, applicationId, updateConfirmationRequest)
        return ApiResponse.success(response)
    }
}