package kr.mashup.branding.ui.schedule;

import javax.validation.Valid;

import kr.mashup.branding.EmptyResponse;
import kr.mashup.branding.ui.schedule.response.QrCodeResponse;
import kr.mashup.branding.ui.schedule.request.QrCodeGenerateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.ApiOperation;
import kr.mashup.branding.facade.schedule.ScheduleFacadeService;
import kr.mashup.branding.ui.ApiResponse;
import kr.mashup.branding.ui.schedule.request.ScheduleUpdateRequest;
import kr.mashup.branding.ui.schedule.response.ScheduleResponse;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/schedules")
@RestController
public class ScheduleController {

    private final ScheduleFacadeService scheduleFacadeService;


    @ApiOperation("스케줄 조회")
    @GetMapping
    public ApiResponse<List<ScheduleResponse>> getSchedules(
            @RequestParam(defaultValue = "12", required = false) Integer generationNumber,
            @PageableDefault Pageable pageable
    ) {
        final Page<ScheduleResponse> responses
                = scheduleFacadeService.getSchedules(generationNumber, pageable);

        return ApiResponse.success(responses);
    }


    @ApiOperation("스케줄 생성")
    @PostMapping
    public ApiResponse<ScheduleResponse> createSchedule(
        @RequestParam(defaultValue = "12", required = false) Integer generationNumber,
        @Valid @RequestBody ScheduleUpdateRequest scheduleUpdateRequest
    ) {
        final ScheduleResponse response
            = scheduleFacadeService.create(generationNumber, scheduleUpdateRequest);

        return ApiResponse.success(response);
    }

    @ApiOperation("스케줄 배포")
    @PostMapping("/{id}/publish")
    public ApiResponse<EmptyResponse> publishSchedule(
        @PathVariable("id") Long scheduleId
    ){
        scheduleFacadeService.publishSchedule(scheduleId);

        return ApiResponse.success(EmptyResponse.of());
    }

    @ApiOperation("스케줄 배포 취소")
    @PostMapping("/{id}/hide")
    public ApiResponse<EmptyResponse> hideSchedule(
            @PathVariable("id") Long scheduleId
    ){
        scheduleFacadeService.hideSchedule(scheduleId);

        return ApiResponse.success(EmptyResponse.of());
    }

    @ApiOperation("스케줄 변경")
    @DeleteMapping("/{id}")
    public ApiResponse<EmptyResponse> updateSchedule(
            @PathVariable("id") Long scheduleId,
            @Valid @RequestBody ScheduleUpdateRequest scheduleUpdateRequest
    ){
        scheduleFacadeService.updateSchedule(scheduleId, scheduleUpdateRequest);

        return ApiResponse.success(EmptyResponse.of());
    }

    @ApiOperation("스케줄 삭제")
    @DeleteMapping("/{id}/hide")
    public ApiResponse<EmptyResponse> deleteSchedule(
            @PathVariable("id") Long scheduleId
    ){
        scheduleFacadeService.deleteSchedule(scheduleId);

        return ApiResponse.success(EmptyResponse.of());
    }

    @ApiOperation("QR Code 생성")
    @PostMapping("/{scheduleId}/event/{eventId}/qr")
    public ApiResponse<QrCodeResponse> deleteSchedule(
            @PathVariable("scheduleId") Long scheduleId,
            @PathVariable("eventId") Long eventId,
            @Valid @RequestBody QrCodeGenerateRequest request
    ){
        final QrCodeResponse response
                = scheduleFacadeService.generateQrCode(scheduleId, eventId, request);

        return ApiResponse.success(response);
    }

}
