package kr.mashup.branding.ui.schedule;

import javax.validation.Valid;

import kr.mashup.branding.EmptyResponse;
import kr.mashup.branding.domain.schedule.ScheduleType;
import kr.mashup.branding.ui.schedule.request.ScheduleUpdateRequest;
import kr.mashup.branding.ui.schedule.response.QrCodeResponse;
import kr.mashup.branding.ui.schedule.request.QrCodeGenerateRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.ApiOperation;
import kr.mashup.branding.facade.schedule.ScheduleFacadeService;
import kr.mashup.branding.ui.ApiResponse;
import kr.mashup.branding.ui.schedule.request.ScheduleCreateRequest;
import kr.mashup.branding.ui.schedule.response.ScheduleResponse;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/schedules")
@RestController
@Slf4j
public class ScheduleController {

    private final ScheduleFacadeService scheduleFacadeService;

    @ApiOperation("스케줄 조회")
    @GetMapping
    public ApiResponse<List<ScheduleResponse>> getSchedules(
            @RequestParam(defaultValue = "14", required = false) Integer generationNumber,
            @RequestParam(defaultValue = "ALL", required = false) ScheduleType scheduleType,
            @RequestParam(required = false) String searchWord,
            @PageableDefault Pageable pageable
    ) {
        final Page<ScheduleResponse> responses =
            scheduleFacadeService.getSchedules(generationNumber, searchWord, scheduleType, pageable);

        return ApiResponse.success(responses);
    }

    @ApiOperation("스케줄 상세 조회")
    @GetMapping("/{scheduleId}")
    public ApiResponse<ScheduleResponse> getSchedule(
            @PathVariable Long scheduleId
    ) {
        final ScheduleResponse response
                = scheduleFacadeService.getSchedule(scheduleId);

        return ApiResponse.success(response);
    }

    @ApiOperation("스케줄 생성")
    @PostMapping
    public ApiResponse<ScheduleResponse> createSchedule(
        @RequestParam(defaultValue = "13", required = false) Integer generationNumber,
        @Valid @RequestBody ScheduleCreateRequest scheduleCreateRequest
    ) {
        final ScheduleResponse response
            = scheduleFacadeService.create(generationNumber, scheduleCreateRequest);

        return ApiResponse.success(response);
    }

    @ApiOperation("스케줄 배포")
    @PostMapping("/{scheduleId}/publish")
    public ApiResponse<EmptyResponse> publishSchedule(
        @PathVariable Long scheduleId
    ){
        scheduleFacadeService.publishSchedule(scheduleId);

        return ApiResponse.success(EmptyResponse.of());
    }

    @ApiOperation("스케줄 배포 취소")
    @PostMapping("/{scheduleId}/hide")
    public ApiResponse<EmptyResponse> hideSchedule(
            @PathVariable Long scheduleId
    ){
        scheduleFacadeService.hideSchedule(scheduleId);

        return ApiResponse.success(EmptyResponse.of());
    }

    @ApiOperation("스케줄 변경")
    @PutMapping("/{scheduleId}")
    public ApiResponse<ScheduleResponse> updateSchedule(
            @PathVariable Long scheduleId,
            @Valid @RequestBody ScheduleUpdateRequest scheduleUpdateRequest
    ){
        ScheduleResponse response = scheduleFacadeService.updateSchedule(scheduleId, scheduleUpdateRequest);

        return ApiResponse.success(response);
    }

    @ApiOperation("스케줄 삭제")
    @DeleteMapping("/{scheduleId}")
    public ApiResponse<EmptyResponse> deleteSchedule(
            @PathVariable Long scheduleId
    ){
        scheduleFacadeService.deleteSchedule(scheduleId);

        return ApiResponse.success(EmptyResponse.of());
    }

    @ApiOperation("QR Code 시간 수정")
    @PostMapping("/{scheduleId}/event/{eventId}/qr")
    public ApiResponse<QrCodeResponse> updateQrCode(
            @PathVariable("scheduleId") Long scheduleId,
            @PathVariable("eventId") Long eventId,
            @Valid @RequestBody QrCodeGenerateRequest request
    ){
        final QrCodeResponse response
                = scheduleFacadeService.updateQrCode(scheduleId, eventId, request);

        return ApiResponse.success(response);
    }

}
