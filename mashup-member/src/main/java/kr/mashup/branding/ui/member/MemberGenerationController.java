package kr.mashup.branding.ui.member;

import io.swagger.annotations.ApiOperation;
import kr.mashup.branding.facade.member.MemberFacadeService;
import kr.mashup.branding.security.MemberAuth;
import kr.mashup.branding.ui.ApiResponse;
import kr.mashup.branding.ui.member.request.MemberGenerationRequest;
import kr.mashup.branding.ui.member.response.MemberGenerationsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/member-generations")
@RequiredArgsConstructor
public class MemberGenerationController {

    private final MemberFacadeService memberFacadeService;
    @ApiOperation("멤버 기수 정보 조회")
    @GetMapping("/my")
    public ApiResponse<MemberGenerationsResponse> findMy(
            @ApiIgnore MemberAuth memberAuth
    ) {
        final MemberGenerationsResponse response
                = memberFacadeService.findMemberGenerationByMemberId(memberAuth.getMemberId());

        return ApiResponse.success(response);
    }

    @ApiOperation("멤버 기수 정보 업데이트")
    @PatchMapping("{id}")
    public ApiResponse<Boolean> updateMy(
            @ApiIgnore MemberAuth memberAuth,
            @PathVariable Long id,
            @Valid @RequestBody MemberGenerationRequest request
    ) {
        final Boolean response
                = memberFacadeService.updateMemberGenerationById(memberAuth.getMemberId(), id, request);

        return ApiResponse.success(response);
    }
}
