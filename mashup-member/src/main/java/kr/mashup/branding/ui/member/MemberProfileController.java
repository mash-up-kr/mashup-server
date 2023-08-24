package kr.mashup.branding.ui.member;

import io.swagger.annotations.ApiOperation;
import kr.mashup.branding.facade.member.MemberProfileFacadeService;
import kr.mashup.branding.security.MemberAuth;
import kr.mashup.branding.ui.ApiResponse;
import kr.mashup.branding.ui.member.request.MemberProfileRequest;
import kr.mashup.branding.ui.member.response.MemberProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/member-profiles")
@RequiredArgsConstructor
public class MemberProfileController {

    private final MemberProfileFacadeService memberProfileFacadeService;
    @ApiOperation("멤버 프로필 조회")
    @GetMapping("/my")
    public ApiResponse<MemberProfileResponse> getMyProfile(
            @ApiIgnore MemberAuth memberAuth
    ) {
        final MemberProfileResponse response
                = memberProfileFacadeService.getMyProfile(memberAuth.getMemberId());

        return ApiResponse.success(response);
    }

    @ApiOperation("멤버 프로필 업데이트")
    @PatchMapping("/my")
    public ApiResponse<Boolean> updateMyProfile(
            @ApiIgnore MemberAuth memberAuth,
            @Valid @RequestBody MemberProfileRequest request
    ) {
        final Boolean response
                = memberProfileFacadeService.updateMyProfile(memberAuth.getMemberId(), request);

        return ApiResponse.success(response);
    }
}
