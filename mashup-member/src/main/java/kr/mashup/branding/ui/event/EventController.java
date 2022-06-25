package kr.mashup.branding.ui.event;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import kr.mashup.branding.domain.event.Event;
import kr.mashup.branding.domain.event.EventCreateVo;
import kr.mashup.branding.service.event.EventService;
import kr.mashup.branding.ui.ApiResponse;
import kr.mashup.branding.ui.event.request.EventCreateRequest;
import kr.mashup.branding.ui.event.response.EventResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v1/events")
@RestController
public class EventController {

	private final EventService eventService;

	@ApiOperation("이벤트 생성")
	@PostMapping()
	public ApiResponse<EventResponse> create(
		@RequestBody EventCreateRequest eventCreateRequest
	) {
		Event event = eventService.create(
			EventCreateVo.of(
				eventCreateRequest.getStartedAt(),
				eventCreateRequest.getEndedAt(),
				eventCreateRequest.getScheduleId()
			));
		return ApiResponse.success(EventResponse.of(event));
	}
}
