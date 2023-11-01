package kr.mashup.branding.ui.member;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import kr.mashup.branding.facade.member.MemberProfileFacadeService;
import kr.mashup.branding.security.MemberAuth;
import kr.mashup.branding.ui.ApiResponse;
import kr.mashup.branding.ui.member.request.MemberProfileRequest;
import kr.mashup.branding.ui.member.response.MemberProfileResponse;
import lombok.RequiredArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("api/v1/member-profiles")
@RequiredArgsConstructor
public class MemberProfileController {

    private final MemberProfileFacadeService memberProfileFacadeService;

    @ApiOperation("나의 프로필 조회")
    @GetMapping("/my")
    public ApiResponse<MemberProfileResponse> getMyProfile(
            @ApiIgnore MemberAuth memberAuth
    ) {
        final MemberProfileResponse response
                = memberProfileFacadeService.getMyProfile(memberAuth.getMemberId());

        return ApiResponse.success(response);
    }

    @ApiOperation("나의 프로필 업데이트")
    @PatchMapping("/my")
    public ApiResponse<Boolean> updateMyProfile(
            @ApiIgnore MemberAuth memberAuth,
            @Valid @RequestBody MemberProfileRequest request
    ) {
        final Boolean response
                = memberProfileFacadeService.updateMyProfile(memberAuth.getMemberId(), request);

        return ApiResponse.success(response);
    }

    @ApiOperation("멤버 프로필 조회")
    @GetMapping("/{memberId}")
    public ApiResponse<MemberProfileResponse> getMemberProfile(@PathVariable Long memberId) {
        final MemberProfileResponse response
            = memberProfileFacadeService.getMyProfile(memberId);

        return ApiResponse.success(response);
    }
}
