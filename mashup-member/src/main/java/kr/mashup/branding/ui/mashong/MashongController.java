package kr.mashup.branding.ui.mashong;

import io.swagger.annotations.ApiOperation;
import kr.mashup.branding.facade.mashong.MashongFacadeService;
import kr.mashup.branding.security.MemberAuth;
import kr.mashup.branding.ui.ApiResponse;
import kr.mashup.branding.ui.mashong.request.MashongFeedRequest;
import kr.mashup.branding.ui.mashong.response.MashongFeedResponse;
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

    @ApiOperation(
            value = "매숑이 팝콘 조회"
    )
    @GetMapping("/popcorn")
    public ApiResponse<Long> popcorn(
            @ApiIgnore MemberAuth memberAuth
    ) {
        return ApiResponse.success(mashongFacadeService.getPopcornCount(memberAuth.getMemberGenerationId()));
    }
}
