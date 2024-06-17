package kr.mashup.branding.ui.mashong;

import io.swagger.annotations.ApiOperation;
import kr.mashup.branding.facade.mashong.MashongFacadeService;
import kr.mashup.branding.security.MemberAuth;
import kr.mashup.branding.ui.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

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
        value = "매숑이 팝콘주기"
    )
    @PostMapping("/popcorn")
    public ApiResponse<Boolean> popcorn(
        @ApiIgnore MemberAuth memberAuth,
        Long missionLevelId
    ) {
        Boolean result = mashongFacadeService.popcorn(memberAuth.getMemberGenerationId(), missionLevelId);
        return ApiResponse.success(result);
    }
}
