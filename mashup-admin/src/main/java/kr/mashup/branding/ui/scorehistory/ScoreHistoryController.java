package kr.mashup.branding.ui.scorehistory;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import kr.mashup.branding.facade.scorehistory.ScoreHistoryFacadeService;
import kr.mashup.branding.ui.ApiResponse;
import kr.mashup.branding.ui.scorehistory.request.ScoreHistoryCreateRequest;
import kr.mashup.branding.ui.scorehistory.response.ScoreHistoryResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v1/score-history")
@RestController
public class ScoreHistoryController {

    private final ScoreHistoryFacadeService scoreHistoryFacadeService;

    @ApiOperation("추가 점수")
    @PostMapping
    public ApiResponse<ScoreHistoryResponse> create(
        @Valid @RequestBody ScoreHistoryCreateRequest scoreHistoryCreateRequest
    ) {
        ScoreHistoryResponse res = scoreHistoryFacadeService.save(scoreHistoryCreateRequest);

        return ApiResponse.success(res);
    }
}
