package kr.mashup.branding.ui.scorehistory;

import io.swagger.annotations.ApiOperation;
import kr.mashup.branding.facade.scorehistory.ScoreHistoryFacadeService;
import kr.mashup.branding.security.MemberAuth;
import kr.mashup.branding.ui.ApiResponse;
import kr.mashup.branding.ui.scorehistory.response.ScoreHistoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@RequestMapping("/api/v1/score-history")
@RequiredArgsConstructor
public class ScoreHistoryController {

    private final ScoreHistoryFacadeService scoreHistoryFacadeService;

    @ApiOperation("점수 히스토리 목록")
    @GetMapping
    public ApiResponse<List<ScoreHistoryResponse>> getScoreHistory(@ApiIgnore MemberAuth memberAuth) {
        List<ScoreHistoryResponse> scoreHistoryResponses = scoreHistoryFacadeService.getScoreHistory(memberAuth.getMemberId());
        return ApiResponse.success(scoreHistoryResponses);
    }
}
