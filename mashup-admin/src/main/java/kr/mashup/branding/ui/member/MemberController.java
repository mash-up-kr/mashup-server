package kr.mashup.branding.ui.member;


import io.swagger.annotations.ApiOperation;
import kr.mashup.branding.domain.member.Platform;
import kr.mashup.branding.facade.member.MemberFacadeService;
import kr.mashup.branding.ui.ApiResponse;
import kr.mashup.branding.ui.member.response.MemberDetailResponse;
import kr.mashup.branding.ui.member.response.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


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
        @RequestParam(value = "platform", required = false) Platform platformName
    ) {
        Page<MemberResponse> response;

        if (platformName == null) {
            response = memberFacadeService.getAllActive(generationNumber, pageable);
        }else{
            response = memberFacadeService.getAllActiveByPlatform(generationNumber, platformName, pageable);
        }

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

}
