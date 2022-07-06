package kr.mashup.branding.ui.event;

import io.swagger.annotations.ApiOperation;
import kr.mashup.branding.facade.event.EventFacadeService;
import kr.mashup.branding.ui.ApiResponse;
import kr.mashup.branding.ui.event.request.EventCreateRequest;
import kr.mashup.branding.ui.event.response.EventResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/events")
@RestController
public class EventController {

    private final EventFacadeService eventFacadeService;

    @ApiOperation("이벤트 생성")
    @PostMapping()
    public ApiResponse<EventResponse> create(
        @RequestBody EventCreateRequest eventCreateRequest
    ) {
        EventResponse res = eventFacadeService.create(eventCreateRequest);

        return ApiResponse.success(res);
    }
}
