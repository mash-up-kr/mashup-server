package kr.mashup.branding.ui.applicant;

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
import kr.mashup.branding.domain.applicant.LoginResponseVo;
import kr.mashup.branding.facade.login.LoginFacadeService;
import kr.mashup.branding.ui.ApiResponse;
import lombok.RequiredArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

@RequiredArgsConstructor
@RequestMapping("/api/v1/applicants")
@RestController
public class ApplicantController {
    private final LoginFacadeService loginFacadeService;
    private final ApplicantService applicantService;
    private final ApplicantAssembler applicantAssembler;

    @ApiOperation("로그인")
    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(
        @RequestBody LoginRequest loginRequest
    ) {
        LoginResponseVo loginResponseVo = loginFacadeService.login(
            applicantAssembler.toLoginRequestVo(loginRequest));
        return ApiResponse.success(applicantAssembler.toLoginResponse(loginResponseVo));
    }

    @ApiOperation("내 정보 조회")
    @GetMapping("/me")
    public ApiResponse<ApplicantResponse> getMe(
        @ApiIgnore @ModelAttribute("applicantId") Long applicantId
    ) {
        return ApiResponse.success(applicantAssembler.toApplicantResponse(applicantService.getApplicant(applicantId)));
    }
}
