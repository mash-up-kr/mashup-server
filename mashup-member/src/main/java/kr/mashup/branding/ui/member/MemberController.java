package kr.mashup.branding.ui.member;

import kr.mashup.branding.facade.member.MemberFacadeService;
import kr.mashup.branding.security.MemberAuth;
import kr.mashup.branding.ui.ApiResponse;
import kr.mashup.branding.ui.member.request.LoginRequest;
import kr.mashup.branding.ui.member.request.SignUpRequest;
import kr.mashup.branding.ui.member.request.ValidInviteRequest;
import kr.mashup.branding.ui.member.response.LoginResponse;
import kr.mashup.branding.ui.member.response.MemberInfoResponse;
import kr.mashup.branding.ui.member.response.SignUpResponse;
import kr.mashup.branding.ui.member.response.ValidInviteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberFacadeService memberFacadeService;

    //1. 회원 정보 조회 Api
    @GetMapping
    public ApiResponse<MemberInfoResponse> getMemberInfo(
        @ApiIgnore MemberAuth memberAuth
    ) {
        MemberInfoResponse memberInfoResponse =
            memberFacadeService.getMemberInfo(memberAuth.getMemberId());

        return ApiResponse.success(memberInfoResponse);
    }

    //2. 로그인
    @PostMapping("login")
    public ApiResponse<LoginResponse> login(
        @Valid @RequestBody LoginRequest request
    ) {
        LoginResponse memberLoginResponse = memberFacadeService.login(request);

        return ApiResponse.success(memberLoginResponse);
    }

    //3. 회원가입
    @PostMapping("signup")
    public ApiResponse<SignUpResponse> signUp(
        @Valid @RequestBody SignUpRequest request
    ) {
        SignUpResponse res = memberFacadeService.signUp(request);

        return ApiResponse.success(res);
    }

    //4. 회원가입 코드 검증
    @GetMapping("code")
    public ApiResponse<ValidInviteResponse> validateSignUpCode(ValidInviteRequest req) {
        ValidInviteResponse response =
            memberFacadeService.validateInviteCode(req);

        return ApiResponse.success(response);
    }

    //5. 회원탈퇴
    @DeleteMapping("/{memberId}")
    public ApiResponse<Void> withdraw(
        @PathVariable Long memberId
    ) {
        memberFacadeService.withdraw(memberId);

        return ApiResponse.success();
    }

}
