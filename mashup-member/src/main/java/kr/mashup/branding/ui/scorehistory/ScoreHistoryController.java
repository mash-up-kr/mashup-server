package kr.mashup.branding.ui.scorehistory;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import kr.mashup.branding.facade.scorehistory.ScoreHistoryFacadeService;
import kr.mashup.branding.security.MemberAuth;
import kr.mashup.branding.ui.ApiResponse;
import kr.mashup.branding.ui.scorehistory.response.ScoreHistoriesResponse;
import kr.mashup.branding.ui.scorehistory.response.ScoreHistoryResponse;
import lombok.RequiredArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/v1/score-history")
@RequiredArgsConstructor
public class ScoreHistoryController {

    private final ScoreHistoryFacadeService scoreHistoryFacadeService;

    @ApiOperation("점수 히스토리 목록")
    @GetMapping
    public ApiResponse<ScoreHistoriesResponse> getScoreHistory(@ApiIgnore MemberAuth memberAuth) {

        final List<ScoreHistoryResponse> scoreHistoryResponses
                = scoreHistoryFacadeService.getScoreHistory(memberAuth.getMemberId());

        return ApiResponse.success(ScoreHistoriesResponse.of(scoreHistoryResponses));
    }

    @ApiOperation("멤버 점수")
    @GetMapping("/{memberId}")
    public ApiResponse<ScoreHistoryResponse> getScore(@PathVariable Long memberId) {

        final ScoreHistoryResponse scoreHistoryResponses
            = scoreHistoryFacadeService.getScoreHistory(memberId)
            .stream().findFirst()
            .orElse(null);

        return ApiResponse.success(scoreHistoryResponses);
    }
}
