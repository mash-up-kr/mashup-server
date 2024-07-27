package kr.mashup.branding.ui.mashong;

import io.swagger.annotations.ApiOperation;
import kr.mashup.branding.facade.mashong.MashongFacadeService;
import kr.mashup.branding.security.MemberAuth;
import kr.mashup.branding.ui.ApiResponse;
import kr.mashup.branding.ui.mashong.request.MashongFeedRequest;
import kr.mashup.branding.ui.mashong.request.MashongLevelUpRequest;
import kr.mashup.branding.ui.mashong.response.MashongFeedResponse;
import kr.mashup.branding.ui.mashong.response.MashongLevelUpResponse;
import kr.mashup.branding.ui.mashong.response.PlatformMashongStatusResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("api/v1/mashong")
@RequiredArgsConstructor
public class MashongController {

    private final MashongFacadeService mashongFacadeService;

    @ApiOperation(
            value = "매숑이 출석"
    )
    @PostMapping("/attend")
    public ApiResponse<Boolean> attend(
            @ApiIgnore MemberAuth memberAuth
    ) {
        Boolean result = mashongFacadeService.attend(memberAuth.getMemberGenerationId());
        return ApiResponse.success(result);
    }

    @ApiOperation(
            value = "매숑이 팝콘 받기"
    )
    @PostMapping("/popcorn")
    public ApiResponse<Boolean> compensatePopcorn(
            @ApiIgnore MemberAuth memberAuth,
            Long missionLevelId
    ) {
        Boolean result = mashongFacadeService.compensatePopcorn(memberAuth.getMemberGenerationId(), missionLevelId);
        return ApiResponse.success(result);
    }

    @ApiOperation(value = "매숑이 팝콘 먹이기")
    @PostMapping("/popcorn/feed")
    public ApiResponse<MashongFeedResponse> feedPopcorn(
            @ApiIgnore MemberAuth memberAuth,
            @Valid @RequestBody MashongFeedRequest request
    ) {
        MashongFeedResponse result = mashongFacadeService.feedPopcorn(
                memberAuth.getMemberGenerationId(),
                request.getPopcornCount()
        );
        return ApiResponse.success(result);
    }

    @ApiOperation(value = "매숑이 레벨업")
    @PostMapping("/level-up")
    public ApiResponse<MashongLevelUpResponse> levelUp(
            @ApiIgnore MemberAuth memberAuth,
            @RequestBody MashongLevelUpRequest request
    ) {
        MashongLevelUpResponse result = mashongFacadeService.levelUp(
                memberAuth.getMemberGenerationId(),
                request.getGoalLevel()
        );
        return ApiResponse.success(result);
    }

    @ApiOperation(
            value = "매숑이 팝콘 조회"
    )
    @GetMapping("/popcorn")
    public ApiResponse<Long> popcorn(
            @ApiIgnore MemberAuth memberAuth
    ) {
        return ApiResponse.success(mashongFacadeService.getPopcornCount(memberAuth.getMemberGenerationId()));
    }

    @ApiOperation(value = "플랫폼 매숑이 현재 상태 조회")
    @GetMapping("/status")
    public ApiResponse<PlatformMashongStatusResponse> readStatus(@ApiIgnore MemberAuth memberAuth) {
        PlatformMashongStatusResponse result = mashongFacadeService.readCurrentStatus(memberAuth.getMemberGenerationId());
        return ApiResponse.success(result);
    }

    @ApiOperation(value = "매숑이와 함께한 날 Days count")
    @GetMapping("/with-mashong-days")
    public ApiResponse<Long> withMashongDays(@ApiIgnore MemberAuth memberAuth) {
        Long withMashongDays = mashongFacadeService.withMashongDaysCount(memberAuth.getMemberGenerationId());
        return ApiResponse.success(withMashongDays);
    }
}
