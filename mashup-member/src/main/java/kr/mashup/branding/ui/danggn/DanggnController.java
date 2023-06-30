package kr.mashup.branding.ui.danggn;

import java.util.List;

import kr.mashup.branding.ui.danggn.request.DanggnRankingRewardRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import kr.mashup.branding.aop.cipher.CheckApiCipherTime;
import kr.mashup.branding.facade.danggn.DanggnFacadeService;
import kr.mashup.branding.security.MemberAuth;
import kr.mashup.branding.ui.ApiResponse;
import kr.mashup.branding.ui.danggn.request.DanggnScoreAddRequest;
import kr.mashup.branding.ui.danggn.response.DanggnMemberRankData;
import kr.mashup.branding.ui.danggn.response.DanggnMemberRankResponse;
import kr.mashup.branding.ui.danggn.response.DanggnPlatformRankResponse;
import kr.mashup.branding.ui.danggn.response.DanggnRandomMessageResponse;
import kr.mashup.branding.ui.danggn.response.DanggnRankingRoundResponse;
import kr.mashup.branding.ui.danggn.response.DanggnRankingRoundsResponse;
import kr.mashup.branding.ui.danggn.response.DanggnScoreResponse;
import kr.mashup.branding.ui.danggn.response.GoldenDanggnPercentResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

@Slf4j
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
    @CheckApiCipherTime(alwaysRequired = false)
    public ApiResponse<DanggnScoreResponse> addDanggnScore(
        @ApiIgnore MemberAuth auth,
        @RequestBody DanggnScoreAddRequest req,
        @RequestHeader(value = "cipher", required = false) String cipher // for swagger, used in aop
    ) {
        DanggnScoreResponse response = danggnFacadeService.addScore(auth.getMemberGenerationId(), req.getScore());
        return ApiResponse.success(response);
    }


    @ApiOperation(value = "당근 흔들기 개인별 랭킹")
    @GetMapping("/rank/member")
    public ApiResponse<List<DanggnMemberRankData>> getMemberRank(
        @RequestParam(defaultValue = "13", required = false) Integer generationNumber,
        @RequestParam(defaultValue = "11", required = false) Integer limit,
        @RequestParam(required = false) Long danggnRankingRoundId
    ) {
        List<DanggnMemberRankData> danggnMemberRankDataList = danggnFacadeService.getMemberRankList(generationNumber, danggnRankingRoundId);
        return ApiResponse.success(danggnMemberRankDataList.subList(0, Math.min(danggnMemberRankDataList.size(), limit)));
    }

    @ApiOperation(value = "당근 흔들기 개인별 랭킹 전체")
    @GetMapping("/rank/member/all")
    public ApiResponse<DanggnMemberRankResponse> getAllMemberRank(
        @RequestParam(defaultValue = "13", required = false) Integer generationNumber,
        @RequestParam(required = false) Long danggnRankingRoundId
    ) {
        return ApiResponse.success(DanggnMemberRankResponse.of(
            danggnFacadeService.getMemberRankList(generationNumber, danggnRankingRoundId),
            11
        ));
    }

    @ApiOperation(value = "당근 흔들기 플랫폼별 랭킹")
    @GetMapping("/rank/platform")
    public ApiResponse<List<DanggnPlatformRankResponse>> getPlatformRank(
        @RequestParam(defaultValue = "13", required = false) Integer generationNumber,
        @RequestParam(required = false) Long danggnRankingRoundId
    ) {
        return ApiResponse.success(danggnFacadeService.getPlatformRankList(generationNumber, danggnRankingRoundId));
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

    @ApiOperation(value = "당근 랭킹 회차 다건 조회")
    @GetMapping("/ranking-round")
    public ApiResponse<DanggnRankingRoundsResponse> getAllRankingRound(@ApiIgnore MemberAuth auth) {
        return ApiResponse.success(danggnFacadeService.getAllRankingRoundByMemberGeneration(auth.getMemberGenerationId()));
    }

    @ApiOperation(value = "당근 랭킹 회차 단건 조회")
    @GetMapping("/ranking-round/{danggnRankingRoundId}")
    public ApiResponse<DanggnRankingRoundResponse> getRankingRoundById(@PathVariable Long danggnRankingRoundId) {
        return ApiResponse.success(danggnFacadeService.getRankingRoundById(danggnRankingRoundId));
    }

    @ApiOperation(value = "당근 1등 리워드 코멘트 작성")
    @PostMapping("/ranking-reward-comment/{danggnRankingRewardId}")
    public ApiResponse<Boolean> writeDanggnRankingRewardComment(
        @ApiIgnore MemberAuth memberAuth,
        @RequestBody DanggnRankingRewardRequest request,
        @PathVariable Long danggnRankingRewardId
    ) {
        danggnFacadeService.writeDanggnRankingRewardComment(memberAuth.getMemberId(), danggnRankingRewardId, request.getComment());
        return ApiResponse.success(true);
    }

    @ApiOperation(value = "당근 랭킹 회차 단건 조회")
    @GetMapping("/ranking-round/test/{danggnRankingRoundId}")
    public ApiResponse<DanggnRankingRoundResponse> getRankingRoundBytestId(@PathVariable Long danggnRankingRoundId) {
        return ApiResponse.success(danggnFacadeService.getRankingRoundById(danggnRankingRoundId));
    }
}
