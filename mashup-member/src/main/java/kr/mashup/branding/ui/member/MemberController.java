package kr.mashup.branding.ui.member;

import kr.mashup.branding.facade.member.MemberFacadeService;
import kr.mashup.branding.security.MemberAuth;
import kr.mashup.branding.ui.ApiResponse;
import kr.mashup.branding.ui.member.dto.request.LoginRequest;
import kr.mashup.branding.ui.member.dto.request.ValidInviteRequest;
import kr.mashup.branding.ui.member.dto.request.SignUpRequest;
import kr.mashup.branding.ui.member.dto.response.LoginResponse;
import kr.mashup.branding.ui.member.dto.response.MemberInfoResponse;
import kr.mashup.branding.ui.member.dto.response.ValidInviteResponse;
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
    public ApiResponse<?> getMemberInfo(
            @ApiIgnore MemberAuth memberAuth
    ){
        MemberInfoResponse memberInfoResponse = memberFacadeService.getMemberInfo(memberAuth.getMemberId());

        return ApiResponse.success(memberInfoResponse);
    }
    //2. 로그인
    @PostMapping("login")
    public ApiResponse<?> login(
            @Valid @RequestBody LoginRequest request
    ){
        LoginResponse memberLoginResponse = memberFacadeService.login(request);

        return ApiResponse.success(memberLoginResponse);
    }
    //3. 회원가입
    @PostMapping("signup")
    public ApiResponse<?> signUp(
            @Valid @RequestBody SignUpRequest request
    ){
        memberFacadeService.signUp(request);
        return ApiResponse.success();
    }

    //4. 회원가입 코드 검증
    @GetMapping("code")
    public ApiResponse<?> validateSignUpCode(
             @RequestParam String platform,
             @RequestParam String code
    ){
        ValidInviteResponse response = memberFacadeService.validateInviteCode(ValidInviteRequest.of(platform,code));

        return ApiResponse.success(response);
    }

    //5. 회원탈퇴
    @DeleteMapping("/{memberId}")
    public ApiResponse<?> withdraw(
            @PathVariable Long memberId
    ){
        memberFacadeService.withdraw(memberId);

        return ApiResponse.success();
    }

}
