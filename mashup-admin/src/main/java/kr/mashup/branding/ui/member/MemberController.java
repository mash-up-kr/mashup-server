package kr.mashup.branding.ui.member;

import java.util.List;

import kr.mashup.branding.ui.member.request.MemberDropOutRequest;
import kr.mashup.branding.ui.member.request.MemberStatusUpdateRequest;
import kr.mashup.branding.ui.member.request.MemberTransferRequest;
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
        final Page<MemberResponse> response =
            memberFacadeService.getAll(generationNumber, platform, searchName, pageable);

        return ApiResponse.success(response);
    }

    @ApiOperation("멤버 조회")
    @GetMapping("/{generationNumber}/{memberId}")
    public ApiResponse<MemberDetailResponse> getByID(
        @PathVariable Integer generationNumber,
        @PathVariable Long memberId
    ) {
        final MemberDetailResponse response = memberFacadeService.getAttendance(generationNumber, memberId);

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

    @ApiOperation("멤버 전체 기수 활동 내역 삭제 및 멤버 삭제")
    @DeleteMapping
    public ApiResponse<EmptyResponse> withdraw(
        @PathVariable Long memberId
    ) {
        memberFacadeService.withdraw(memberId);

        return ApiResponse.success();
    }

    @ApiOperation("특정 기수 중도 하차 처리")
    @DeleteMapping("/drop-out")
    public ApiResponse<EmptyResponse> dropOut(
        @RequestBody MemberDropOutRequest request
    ) {
        memberFacadeService.dropOut(request);

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

    @ApiOperation("새로운 기수로 멤버 이관")
    @PostMapping("/transfer")
    public ApiResponse<EmptyResponse> transferMemberToNewGeneration(
        @RequestBody MemberTransferRequest memberTransferRequest
    ){
        memberFacadeService.transfer(memberTransferRequest);

        return ApiResponse.success();
    }
}
