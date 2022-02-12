package kr.mashup.branding.ui.applicant;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import kr.mashup.branding.domain.applicant.LoginResponseVo;
import kr.mashup.branding.facade.login.LoginFacadeService;
import kr.mashup.branding.ui.ApiResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v1/applicants")
@RestController
public class ApplicantController {
    private final LoginFacadeService loginFacadeService;
    private final ApplicantAssembler applicantAssembler;

    @ApiOperation("로그인")
    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(
        @RequestBody LoginRequest loginRequest
    ) {
        LoginResponseVo loginResponseVo = loginFacadeService.login(applicantAssembler.toLoginRequestVo(loginRequest));
        return ApiResponse.success(applicantAssembler.toLoginResponse(loginResponseVo));
    }
}

