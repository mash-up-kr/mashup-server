package kr.mashup.branding.ui.mashong;

import io.swagger.annotations.ApiOperation;
import kr.mashup.branding.facade.mashong.MashongMissionFacadeService;
import kr.mashup.branding.security.MemberAuth;
import kr.mashup.branding.service.mashong.dto.MissionStatus;
import kr.mashup.branding.ui.ApiResponse;
import kr.mashup.branding.ui.mashong.response.MashongAttendanceResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
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
    @GetMapping("/status")
    public ApiResponse<List<MissionStatus>> missionStatusList(
            @ApiIgnore MemberAuth memberAuth
    ) {
        return ApiResponse.success(mashongMissionFacadeService.missionStatusList(memberAuth.getMemberGenerationId()));
    }

    @ApiOperation(value = "매숑이 출석 현황")
    @GetMapping("/attendances")
    public ApiResponse<MashongAttendanceResponse> readMashongAttendance(
            @ApiIgnore MemberAuth memberAuth
    ) {
        return ApiResponse.success(mashongMissionFacadeService.readMashongAttendanceStatus(memberAuth.getMemberGenerationId()));
    }
}
