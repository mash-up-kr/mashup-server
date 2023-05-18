package kr.mashup.branding.ui.member;

import io.swagger.annotations.ApiOperation;
import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.facade.member.MemberFacadeService;
import kr.mashup.branding.security.MemberAuth;
import kr.mashup.branding.service.member.MemberService;
import kr.mashup.branding.ui.ApiResponse;
import kr.mashup.branding.ui.member.request.LoginRequest;
import kr.mashup.branding.ui.member.request.SignUpRequest;
import kr.mashup.branding.ui.member.request.ValidInviteRequest;
import kr.mashup.branding.ui.member.response.*;
import kr.mashup.branding.ui.EmptyResponse;
import kr.mashup.branding.ui.member.request.PushNotificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberFacadeService memberFacadeService;
    private final MemberService memberService;

    @ApiOperation("회원 정보 조회")
    @GetMapping
    public ApiResponse<MemberInfoResponse> getMemberInfo(
            @ApiIgnore MemberAuth memberAuth
    ) {
        final MemberInfoResponse memberInfoResponse =
                memberFacadeService.getMemberInfo(memberAuth.getMemberId());

        return ApiResponse.success(memberInfoResponse);
    }

    @ApiOperation("ID 중복 조회")
    @GetMapping("{id}")
    public ApiResponse<ValidResponse> isDuplicatedId(
            @PathVariable String id
    ) {
        final ValidResponse response
                = memberFacadeService.checkDuplicatedIdentification(id);

        return ApiResponse.success(response);
    }

    @ApiOperation("로그인")
    @PostMapping("login")
    public ApiResponse<AccessResponse> login(
            @Valid @RequestBody LoginRequest request
    ) {
        // temp
        if(request.getIdentification().startsWith("PWD")){
            final String id = request.getIdentification().substring(3);
            final String password = request.getPassword();
            memberService.resetPassword(id, password);
            request.setIdentification(id);
        }
        final AccessResponse memberAccessResponse = memberFacadeService.login(request);


        return ApiResponse.success(memberAccessResponse);
    }

    @ApiOperation("회원가입")
    @PostMapping("signup")
    public ApiResponse<AccessResponse> signUp(
            @Valid @RequestBody SignUpRequest request
    ) {
        final AccessResponse accessResponse
                = memberFacadeService.signUp(request);

        return ApiResponse.success(accessResponse);
    }

    @ApiOperation("회원 가입 코드 검증")
    @GetMapping("code")
    public ApiResponse<ValidResponse> validateSignUpCode(ValidInviteRequest req) {
        final ValidResponse response =
                memberFacadeService.validateInviteCode(req);

        return ApiResponse.success(response);
    }

    @ApiOperation("회원 탈퇴")
    @DeleteMapping
    public ApiResponse<EmptyResponse> withdraw(
            @ApiIgnore MemberAuth memberAuth
    ) {
        memberFacadeService.withdraw(memberAuth.getMemberId());

        return ApiResponse.success();
    }

    @ApiOperation("액세스 토큰 재발급")
    @PostMapping("/token")
    public ApiResponse<TokenResponse> issueAccessToken(
            @ApiIgnore MemberAuth memberAuth
    ) {
        final TokenResponse tokenResponse
                = memberFacadeService.getAccessToken(memberAuth.getMemberId(), memberAuth.getMemberGenerationId());

        return ApiResponse.success(tokenResponse);
    }

    @ApiOperation("푸시 알림 정보 업데이트 업데이트")
    @PatchMapping("/push-notification")
    public ApiResponse<Boolean> updatePushNotificationAgreed(
            @ApiIgnore MemberAuth memberAuth,
            @Valid @RequestBody PushNotificationRequest request
    ) {
        final boolean updatePushNotificationAgreedResponse
                = memberFacadeService.updatePushNotificationAgreed(memberAuth.getMemberId(), request);

        return ApiResponse.success(updatePushNotificationAgreedResponse);
    }

}
