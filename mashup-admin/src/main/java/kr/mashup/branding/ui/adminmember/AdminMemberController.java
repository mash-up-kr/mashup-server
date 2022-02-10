package kr.mashup.branding.ui.adminmember;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.mashup.branding.domain.adminmember.AdminMember;
import kr.mashup.branding.domain.adminmember.AdminMemberSignInVo;
import kr.mashup.branding.domain.adminmember.AdminMemberVo;
import kr.mashup.branding.facade.adminmember.AdminMemberFacadeService;
import kr.mashup.branding.facade.adminmember.SignInVo;
import kr.mashup.branding.ui.ApiResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/admin-members")
public class AdminMemberController {

    private final AdminMemberAssembler adminMemberAssembler;
    private final AdminMemberFacadeService adminMemberFacadeService;

    @PostMapping("/sign-up")
    public ApiResponse<SignUpResponse> signup(
        @RequestBody SignUpRequest signUpRequest
    ) {
        AdminMemberVo adminMemberVo = adminMemberAssembler.toAdminMemberVo(signUpRequest);
        AdminMember adminMember = adminMemberFacadeService.signUp(adminMemberVo);
        return ApiResponse.success(adminMemberAssembler.toSignUpResponse(adminMember));
    }

    @PostMapping("/sign-in")
    public ApiResponse<SignInResponse> signin(
        @RequestBody SignInRequest signInRequest
    ) {
        AdminMemberSignInVo adminMemberSignInVo = adminMemberAssembler.toAdminMemberSignInVo(signInRequest);
        SignInVo signInVo = adminMemberFacadeService.signIn(adminMemberSignInVo);
        return ApiResponse.success(adminMemberAssembler.toSignInResponse(signInVo));
    }

}
