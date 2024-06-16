package kr.mashup.branding.ui.mashong;

import io.swagger.annotations.ApiOperation;
import kr.mashup.branding.facade.mashong.MashongMissionFacadeService;
import kr.mashup.branding.security.MemberAuth;
import kr.mashup.branding.service.mashong.dto.MissionStatus;
import kr.mashup.branding.ui.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/mashong-mission")
@RequiredArgsConstructor
public class MashongMissionController {
    private final MashongMissionFacadeService mashongMissionFacadeService;

    @ApiOperation(
        value = "매숑이 미션들 상태",
        notes =
            "<h2>Error Code</h2>" +
                "<p>" +
                "MEMBER_NOT_FOUND</br>" +
                "MEMBER_GENERATION_NOT_FOUND</br>" +
                "</p>"

    )
    @PostMapping("/status")
    public ApiResponse<List<MissionStatus>> missionStatusList(
        @ApiIgnore MemberAuth memberAuth
    ) {
        return ApiResponse.success(mashongMissionFacadeService.missionStatusList(memberAuth.getMemberGenerationId()));
    }
}
