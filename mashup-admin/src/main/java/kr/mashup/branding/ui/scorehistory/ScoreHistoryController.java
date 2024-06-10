package kr.mashup.branding.ui.scorehistory;

import kr.mashup.branding.EmptyResponse;
import kr.mashup.branding.facade.scorehistory.ScoreHistoryProcessor;
import kr.mashup.branding.ui.scorehistory.request.ScoreAddRequest;
import kr.mashup.branding.ui.scorehistory.request.ScoreCancelRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import kr.mashup.branding.facade.scorehistory.ScoreHistoryFacadeService;
import kr.mashup.branding.ui.ApiResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v1/score-history")
@RestController
public class ScoreHistoryController {

    private final ScoreHistoryFacadeService scoreHistoryFacadeService;
    private final ScoreHistoryProcessor scoreHistoryProcessor;

    @ApiOperation("점수 추가")
    @PostMapping("add")
    public ApiResponse<EmptyResponse> addScore(
        @RequestBody ScoreAddRequest request
    ) {
        scoreHistoryFacadeService.addScore(request.getMemberId(), request.getGenerationNumber(), request.getScoreType(), request.getName(), request.getDate(), request.getMemo());
        return ApiResponse.success(EmptyResponse.of());
    }

    @ApiOperation("점수 취소")
    @PostMapping("cancel")
    public ApiResponse<EmptyResponse> cancelScore(
        @RequestBody ScoreCancelRequest request
    ) {
        scoreHistoryFacadeService.cancelScore(request.getScoreHistoryId(), request.getMemo());
        return ApiResponse.success(EmptyResponse.of());
    }

    @PostMapping()
    public void createScore() {
        scoreHistoryProcessor.create();
    }
}
