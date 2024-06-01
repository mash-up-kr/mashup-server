package kr.mashup.branding.ui.mashong;

import io.swagger.annotations.ApiOperation;
import kr.mashup.branding.facade.mashong.MashongFacadeService;
import kr.mashup.branding.security.MemberAuth;
import kr.mashup.branding.service.mashong.dto.MissionStatus;
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
        value = "매숑이 출석",
        notes =
            "<h2>Error Code</h2>" +
                "<p>" +
                "MEMBER_NOT_FOUND</br>" +
                "MEMBER_GENERATION_NOT_FOUND</br>" +
                "</p>"

    )
    @PostMapping("/attend")
    public ApiResponse<Boolean> attend(
        @ApiIgnore MemberAuth memberAuth
    ) {
        Boolean result = mashongFacadeService.attend(memberAuth.getMemberId());
        return ApiResponse.success(result);
    }

    @ApiOperation(
        value = "매숑이 미션들 상태",
        notes =
            "<h2>Error Code</h2>" +
                "<p>" +
                "MEMBER_NOT_FOUND</br>" +
                "MEMBER_GENERATION_NOT_FOUND</br>" +
                "</p>"

    )
    @PostMapping("/mission-status")
    public ApiResponse<MissionStatus> missionStatus(
        @ApiIgnore MemberAuth memberAuth,
        Long missionId
    ) {
        return ApiResponse.success(mashongFacadeService.missionStatus(memberAuth.getMemberGenerationId(), missionId));
    }
}
