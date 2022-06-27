package kr.mashup.branding.ui.member;

import kr.mashup.branding.facade.member.MemberFacadeService;
import kr.mashup.branding.ui.ApiResponse;
import kr.mashup.branding.ui.ApiV1RestController;
import kr.mashup.branding.ui.member.dto.request.LoginRequest;
import kr.mashup.branding.ui.member.dto.request.ValidInviteRequest;
import kr.mashup.branding.ui.member.dto.request.SignUpRequest;
import kr.mashup.branding.ui.member.dto.request.WithdrawRequest;
import kr.mashup.branding.ui.member.dto.response.LoginResponse;
import kr.mashup.branding.ui.member.dto.response.MemberInfoResponse;
import kr.mashup.branding.ui.member.dto.response.ValidInviteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@ApiV1RestController
@RequestMapping("member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberFacadeService memberFacadeService;

    //1. 회원 정보 조회 Api
    @GetMapping
    public ApiResponse<?> getMemberInfo(
            @ApiIgnore @ModelAttribute("memberId") Long memberId
    ){
        MemberInfoResponse memberInfoResponse = memberFacadeService.getMemberInfo(memberId);

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
            @Valid @RequestBody ValidInviteRequest request
    ){
        ValidInviteResponse response = memberFacadeService.validateInviteCode(request);

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
