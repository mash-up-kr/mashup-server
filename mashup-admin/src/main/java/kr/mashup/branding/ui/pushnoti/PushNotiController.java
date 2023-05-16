package kr.mashup.branding.ui.pushnoti;

import io.swagger.annotations.ApiOperation;
import kr.mashup.branding.EmptyResponse;
import kr.mashup.branding.facade.pushnoti.PushNotiFacadeService;
import kr.mashup.branding.ui.ApiResponse;
import kr.mashup.branding.ui.pushnoti.request.BroadCastPushNotiRequest;
import kr.mashup.branding.ui.pushnoti.request.SendMePushNotiRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation("일부 사용자에게 푸시 노티 발송")
    @PostMapping("/narrowcast")
    public ApiResponse<EmptyResponse> sendPushNotiToPartialMembers(
        @Valid @RequestBody SendMePushNotiRequest request
    ) {
        pushNotiFacadeService.sendPushNotiToPartialMembers(
            request.getMemberIds(),
            request.getTitle(),
            request.getBody(),
            request.getKeyType(),
            request.getLinkType());

        return ApiResponse.success(EmptyResponse.of());
    }

}
