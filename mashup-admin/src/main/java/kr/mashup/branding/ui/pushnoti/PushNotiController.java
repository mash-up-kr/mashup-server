package kr.mashup.branding.ui.pushnoti;

import io.swagger.annotations.ApiOperation;
import kr.mashup.branding.EmptyResponse;
import kr.mashup.branding.facade.pushnoti.PushNotiFacadeService;
import kr.mashup.branding.ui.ApiResponse;
import kr.mashup.branding.ui.pushnoti.request.BroadCastPushNotiRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/api/v1/push-notis")
@RestController
public class PushNotiController {
    private final PushNotiFacadeService pushNotiFacadeService;

    @ApiOperation("전체 멤버에게 푸시 노티 발송")
    @PostMapping("/broadcast")
    public ApiResponse<EmptyResponse> sendPushNotiToAllMembers(
        @Valid @RequestBody BroadCastPushNotiRequest request
    ) {
        pushNotiFacadeService.sendPushNotiToAllMembers(request.getTitle(), request.getBody());

        return ApiResponse.success(EmptyResponse.of());
    }
}
