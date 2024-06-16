package kr.mashup.branding.ui.mashong;

import io.swagger.annotations.ApiOperation;
import kr.mashup.branding.domain.mashong.MissionType;
import kr.mashup.branding.facade.mashong.MashongMissionFacadeService;
import kr.mashup.branding.security.MemberAuth;
import kr.mashup.branding.service.mashong.dto.MissionStatus;
import kr.mashup.branding.ui.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("api/v1/mashong-mission")
@RequiredArgsConstructor
public class MashongMissionController {
    private final MashongMissionFacadeService mashongMissionFacadeService;

    @ApiOperation(
        value = "매숑이 미션 상태 조회",
        notes =
            "<h2>Error Code</h2>" +
                "<p>" +
                "MEMBER_NOT_FOUND</br>" +
                "MEMBER_GENERATION_NOT_FOUND</br>" +
                "</p>"

    )
    @GetMapping("/status/{missionId}")
    public ApiResponse<MissionStatus> missionStatus(
        @ApiIgnore MemberAuth memberAuth,
        @PathVariable
        Long missionId
    ) {
        return ApiResponse.success(mashongMissionFacadeService.missionStatus(memberAuth.getMemberGenerationId(), missionId));
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
    @PostMapping("/status")
    public ApiResponse<Map<MissionType, List<MissionStatus>>> missionStatusList(
        @ApiIgnore MemberAuth memberAuth
    ) {
        return ApiResponse.success(mashongMissionFacadeService.missionStatusList(memberAuth.getMemberGenerationId()));
    }
}
