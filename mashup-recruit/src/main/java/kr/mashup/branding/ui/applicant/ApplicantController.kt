package kr.mashup.branding.ui.applicant

import io.swagger.annotations.ApiOperation
import kr.mashup.branding.facade.application.ApplicantFacadeService
import kr.mashup.branding.facade.login.LoginFacadeService
import kr.mashup.branding.ui.ApiResponse
import kr.mashup.branding.ui.applicant.vo.ApplicantResponse
import kr.mashup.branding.ui.applicant.vo.LoginRequest
import kr.mashup.branding.ui.applicant.vo.LoginResponse
import lombok.RequiredArgsConstructor
import org.springframework.web.bind.annotation.*
import springfox.documentation.annotations.ApiIgnore

@RequiredArgsConstructor
@RequestMapping("/api/v1/applicants")
@RestController
class ApplicantController(
    private val loginFacadeService: LoginFacadeService,
    private val applicantFacadeService: ApplicantFacadeService
) {

    @ApiOperation("로그인")
    @PostMapping("/login")
    fun login(
        @RequestBody loginRequest: LoginRequest
    ): ApiResponse<LoginResponse> {
        val response = loginFacadeService.login(loginRequest.googleIdToken)
        return ApiResponse.success(response)
    }

    @ApiOperation("내 정보 조회")
    @GetMapping("/me")
    fun getMe(
        @ApiIgnore @ModelAttribute("applicantId") applicantId: Long
    ): ApiResponse<ApplicantResponse> {
        val response = applicantFacadeService.getApplicant(applicantId)
        return ApiResponse.success(response)
    }
}