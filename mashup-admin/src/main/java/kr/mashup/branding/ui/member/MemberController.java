package kr.mashup.branding.ui.member;

import java.util.List;

import kr.mashup.branding.ui.member.request.MemberStatusUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.ApiOperation;
import kr.mashup.branding.EmptyResponse;
import kr.mashup.branding.domain.member.Platform;
import kr.mashup.branding.facade.member.MemberFacadeService;
import kr.mashup.branding.ui.ApiResponse;
import kr.mashup.branding.ui.member.request.MemberPasswordResetRequest;
import kr.mashup.branding.ui.member.response.MemberDetailResponse;
import kr.mashup.branding.ui.member.response.MemberResponse;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
@RestController
public class MemberController {
    private final MemberFacadeService memberFacadeService;

    @ApiOperation("전체 멤버 조회")
    @GetMapping("/{generationNumber}")
    public ApiResponse<List<MemberResponse>> getAllByGeneration(
        @PageableDefault Pageable pageable,
        @PathVariable Integer generationNumber,
        @RequestParam(value = "platform", required = false) Platform platform,
        @RequestParam(value = "searchName", required = false) String searchName
    ) {
        Page<MemberResponse> response = memberFacadeService.getAllNotRun(generationNumber, platform, searchName, pageable);

        return ApiResponse.success(response);
    }

    @ApiOperation("멤버 조회")
    @GetMapping("/{generationNumber}/{memberId}")
    public ApiResponse<MemberDetailResponse> getByID(
        @PathVariable Integer generationNumber,
        @PathVariable Long memberId
    ){
        MemberDetailResponse response = memberFacadeService.getAttendance(generationNumber, memberId);

        return ApiResponse.success(response);
    }

    @ApiOperation("비밀번호 초기화")
    @PatchMapping("/{id}/reset/password")
    public ApiResponse<EmptyResponse> resetPassword(
        @PathVariable String id,
        @RequestBody MemberPasswordResetRequest request
    ) {
        memberFacadeService.resetPassword(id, request.getNewPassword());

        return ApiResponse.success();
    }

    @ApiOperation("회원 강제 탈퇴")
    @DeleteMapping("/{memberId}")
    public ApiResponse<EmptyResponse> withdraw(
        @PathVariable Long memberId
    ) {
        memberFacadeService.withdraw(memberId);

        return ApiResponse.success();
    }

    @ApiOperation("멤버 상태 변경")
    @PostMapping("/status/{generationNumber}")
    public ApiResponse<EmptyResponse> updateMemberStatus(
            @PathVariable Integer generationNumber,
            @RequestBody MemberStatusUpdateRequest memberStatusUpdateRequest
    ) {
        memberFacadeService.updateMemberStatus(generationNumber, memberStatusUpdateRequest);

        return ApiResponse.success();
    }
}
