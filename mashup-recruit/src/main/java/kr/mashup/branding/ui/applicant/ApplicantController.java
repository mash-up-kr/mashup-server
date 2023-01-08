package kr.mashup.branding.ui.applicant;

import kr.mashup.branding.facade.application.ApplicantFacadeService;
import kr.mashup.branding.ui.applicant.vo.ApplicantResponse;
import kr.mashup.branding.ui.applicant.vo.LoginRequest;
import kr.mashup.branding.ui.applicant.vo.LoginResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import kr.mashup.branding.service.applicant.ApplicantService;
import kr.mashup.branding.facade.login.LoginFacadeService;
import kr.mashup.branding.ui.ApiResponse;
import lombok.RequiredArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

@RequiredArgsConstructor
@RequestMapping("/api/v1/applicants")
@RestController
public class ApplicantController {
    private final LoginFacadeService loginFacadeService;
    private final ApplicantFacadeService applicantFacadeService;
    

    @ApiOperation("로그인")
    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(
        @RequestBody LoginRequest loginRequest
    ) {
        final LoginResponse response = loginFacadeService.login(loginRequest.getGoogleIdToken());

        return ApiResponse.success(response);
    }

    @ApiOperation("내 정보 조회")
    @GetMapping("/me")
    public ApiResponse<ApplicantResponse> getMe(
        @ApiIgnore @ModelAttribute("applicantId") Long applicantId
    ) {
        final ApplicantResponse response = applicantFacadeService.getApplicant(applicantId);

        return ApiResponse.success(response);
    }
}
