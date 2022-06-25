package kr.mashup.branding.ui.schedule;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import kr.mashup.branding.domain.schedule.Schedule;
import kr.mashup.branding.domain.schedule.ScheduleCreateVo;
import kr.mashup.branding.service.schedule.ScheduleService;
import kr.mashup.branding.ui.ApiResponse;
import kr.mashup.branding.ui.schedule.request.ScheduleCreateRequest;
import kr.mashup.branding.ui.schedule.response.ScheduleResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v1/schedules")
@RestController
public class ScheduleController {

	private final ScheduleService scheduleService;

	@ApiOperation("스케줄 생성")
	@PostMapping()
	public ApiResponse<ScheduleResponse> create(
		@Valid @RequestBody ScheduleCreateRequest scheduleCreateRequest
	) {
		Schedule schedule = scheduleService.create(
			ScheduleCreateVo.of(
				scheduleCreateRequest.getName(),
				scheduleCreateRequest.getStartedAt(),
				scheduleCreateRequest.getEndedAt(),
				scheduleCreateRequest.getGenerationId())
		);
		return ApiResponse.success(ScheduleResponse.of(schedule));
	}

	@ApiOperation("스케줄 조회")
	@GetMapping("/{id}")
	public ApiResponse<ScheduleResponse> getById(@PathVariable Long id) {
		Schedule schedule = scheduleService.getByIdOrThrow(id);
		return ApiResponse.success(ScheduleResponse.of(schedule));
	}

	@ApiOperation("기수로 스케줄 조회")
	@GetMapping("generations/{generationNumber}")
	public ApiResponse<List<ScheduleResponse>> getByGenerationNumber(@PathVariable Integer generationNumber) {
		List<Schedule> scheduleList = scheduleService.getByGenerationNumber(generationNumber);
		return ApiResponse.success(
			scheduleList.stream()
				.map(ScheduleResponse::of)
				.collect(Collectors.toList())
		);
	}
}
