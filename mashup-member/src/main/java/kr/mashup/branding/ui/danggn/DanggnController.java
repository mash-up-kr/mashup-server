package kr.mashup.branding.ui.danggn;

import io.swagger.annotations.ApiOperation;
import kr.mashup.branding.facade.danggn.DanggnFacadeService;
import kr.mashup.branding.security.MemberAuth;
import kr.mashup.branding.ui.ApiResponse;
import kr.mashup.branding.ui.danggn.request.DanggnScoreAddRequest;
import kr.mashup.branding.ui.danggn.response.*;
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
        @RequestBody DanggnScoreAddRequest req
    ) {
        DanggnScoreResponse response = danggnFacadeService.addScore(auth.getMemberGenerationId(), req.getScore());
        return ApiResponse.success(response);
    }

    @ApiOperation(value = "당근 흔들기 개인별 랭킹")
    @GetMapping("/rank/member")
    public ApiResponse<List<DanggnMemberRankData>> getMemberRank(
        @RequestParam(defaultValue = "13", required = false) Integer generationNumber,
        @RequestParam(defaultValue = "11", required = false) Integer limit
    ) {
        List<DanggnMemberRankData> danggnMemberRankDataList = danggnFacadeService.getMemberRankList(generationNumber);
        return ApiResponse.success(danggnMemberRankDataList.subList(0, Math.min(danggnMemberRankDataList.size(), limit)));
    }

    @ApiOperation(value = "당근 흔들기 개인별 랭킹 전체")
    @GetMapping("/rank/member/all")
    public ApiResponse<DanggnMemberRankResponse> getAllMemberRank(
        @RequestParam(defaultValue = "13", required = false) Integer generationNumber
    ) {
        return ApiResponse.success(DanggnMemberRankResponse.of(
            danggnFacadeService.getMemberRankList(generationNumber),
            11
        ));
    }

    @ApiOperation(value = "당근 흔들기 플랫폼별 랭킹")
    @GetMapping("/rank/platform")
    public ApiResponse<List<DanggnPlatformRankResponse>> getPlatformRank(
        @RequestParam(defaultValue = "13", required = false) Integer generationNumber
    ) {
        return ApiResponse.success(danggnFacadeService.getPlatformRankList(generationNumber));
    }

    @ApiOperation(value = "황금 당근 확률")
    @GetMapping("/golden-danggn-percent")
    public ApiResponse<GoldenDanggnPercentResponse> getGoldenDanggnPercent() {
        return ApiResponse.success(GoldenDanggnPercentResponse.of(danggnFacadeService.getGoldenDanggnPercent()));
    }

    @ApiOperation(value = "랜덤 오늘의 메시지")
    @GetMapping("/random-today-message")
    public ApiResponse<DanggnRandomMessageResponse> getRandomTodayMessage() {
        return ApiResponse.success(danggnFacadeService.getRandomTodayMessage());
    }
}
