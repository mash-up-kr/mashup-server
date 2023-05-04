package kr.mashup.branding.ui.danggn;

import io.swagger.annotations.ApiOperation;
import kr.mashup.branding.EmptyResponse;
import kr.mashup.branding.facade.danggn.DanggnTodayMessageFacadeService;
import kr.mashup.branding.ui.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/danggn-today-message")
public class DanggnTodayMessageController {
    private final DanggnTodayMessageFacadeService danggnTodayMessageFacadeService;

    @ApiOperation(value = "당근 오늘의 메시지 모두 읽기")
    @GetMapping()
    public ApiResponse<List<TodayMessageResponse>> readTodayMessages() {
        List<TodayMessageResponse> data = danggnTodayMessageFacadeService.readTodayMessages();

        return ApiResponse.success(data);
    }

    @ApiOperation(value = "당근 오늘의 메시지 개별 읽기")
    @GetMapping("{messageId}")
    public ApiResponse<TodayMessageResponse> readTodayMessage(
            @PathVariable("messageId") Long id
    ) {
        TodayMessageResponse data = danggnTodayMessageFacadeService.readTodayMessage(id);

        return ApiResponse.success(data);
    }

    @ApiOperation(value = "당근 오늘의 메시지 생성")
    @PostMapping()
    public ApiResponse<TodayMessageResponse> createTodayMessage(
            @RequestBody TodayMessageRequest todayMessageRequest
    ) {
        TodayMessageResponse data = danggnTodayMessageFacadeService.createTodayMessage(todayMessageRequest);

        return ApiResponse.success(data);
    }

    @ApiOperation(value = "당근 오늘의 메시지 업데이트")
    @PutMapping("{messageId}")
    public ApiResponse<TodayMessageResponse> updateTodayMessage(
            @PathVariable("messageId") Long id,
            @RequestBody TodayMessageRequest todayMessageRequest
    ) {
        TodayMessageResponse data = danggnTodayMessageFacadeService.updateTodayMessage(id, todayMessageRequest);

        return ApiResponse.success(data);
    }

    @ApiOperation(value = "당근 오늘의 메시지 삭제")
    @DeleteMapping("{messageId}")
    public ApiResponse<EmptyResponse> deleteTodayMessage(
            @PathVariable("messageId") Long id
    ) {
        danggnTodayMessageFacadeService.deleteTodayMessage(id);

        return ApiResponse.success();
    }
}
