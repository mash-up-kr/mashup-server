package kr.mashup.branding.ui.danggn;

import io.swagger.annotations.ApiOperation;
import kr.mashup.branding.facade.danggn.DanggnFacadeService;
import kr.mashup.branding.security.MemberAuth;
import kr.mashup.branding.ui.ApiResponse;
import kr.mashup.branding.ui.danggn.request.DanggnScoreAddRequest;
import kr.mashup.branding.ui.danggn.response.DanggnMemberRankResponse;
import kr.mashup.branding.ui.danggn.response.DanggnPlatformRankResponse;
import kr.mashup.branding.ui.danggn.response.DanggnScoreResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@RequestMapping("api/v1/danggn")
@RequiredArgsConstructor
public class DanggnController {
    private final DanggnFacadeService danggnFacadeService;

    @ApiOperation(
        value = "당근 흔들기 점수 추가",
        notes =
            "<h2>Error Code</h2>" +
                "<p>" +
                "MEMBER_NOT_FOUND</br>" +
                "MEMBER_GENERATION_NOT_FOUND</br>" +
                "</p>"

    )
    @PostMapping("/score")
    public ApiResponse<DanggnScoreResponse> addDanggnScore(
        @ApiIgnore MemberAuth auth,
        @RequestBody DanggnScoreAddRequest req,
        @RequestParam Integer generationNumber
    ) {
        DanggnScoreResponse response = danggnFacadeService.addScore(auth.getMemberId(), generationNumber, req.getScore());
        return ApiResponse.success(response);
    }

    @ApiOperation(value = "당근 흔들기 개인별 랭킹")
    @GetMapping("/rank/member")
    public ApiResponse<List<DanggnMemberRankResponse>> getMemberRank(
        @RequestParam(defaultValue = "13", required = false) Integer generationNumber,
        @RequestParam(defaultValue = "11", required = false) Integer limit
    ) {
        return ApiResponse.success(danggnFacadeService.getMemberRankList(generationNumber, limit));
    }

    @ApiOperation(value = "당근 흔들기 플랫폼별 랭킹")
    @GetMapping("/rank/platform")
    public ApiResponse<List<DanggnPlatformRankResponse>> getPlatformRank(
        @RequestParam(defaultValue = "13", required = false) Integer generationNumber
    ) {
        return ApiResponse.success(danggnFacadeService.getPlatformRankList(generationNumber));
    }
}
