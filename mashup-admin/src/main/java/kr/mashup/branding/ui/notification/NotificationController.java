package kr.mashup.branding.ui.notification;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.mashup.branding.domain.notification.NotificationDetailVo;
import kr.mashup.branding.facade.notification.NotificationFacadeService;
import kr.mashup.branding.ui.ApiResponse;
import kr.mashup.branding.ui.notification.sms.SmsSendRequest;
import lombok.RequiredArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "문자 발송 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {
    private final NotificationFacadeService notificationFacadeService;
    private final NotificationAssembler notificationAssembler;

    @ApiOperation("SMS 발송")
    @PostMapping("/sms/send")
    public ApiResponse<NotificationDetailResponse> sendSms(
        @ApiIgnore @ModelAttribute("adminMemberId") Long adminMemberId,
        @RequestBody SmsSendRequest smsSendRequest
    ) {
        NotificationDetailVo notificationDetailVo = notificationFacadeService.sendSms(
            adminMemberId,
            notificationAssembler.toSmsSendVo(smsSendRequest)
        );
        return ApiResponse.success(
            notificationAssembler.toNotificationDetailResponse(notificationDetailVo)
        );
    }

    @ApiOperation("발송내역 목록조회")
    @GetMapping
    public ApiResponse<List<NotificationSimpleResponse>> getNotifications(
        @ApiIgnore @ModelAttribute("adminMemberId") Long adminMemberId,
        @RequestParam(required = false) String searchWord,
        Pageable pageable
    ) {
        return ApiResponse.success(
            notificationFacadeService.getNotifications(adminMemberId, searchWord, pageable)
                .map(notificationAssembler::toNotificationResponse)
        );
    }

    @ApiOperation("발송내역 상세조회")
    @GetMapping("/{notificationId}")
    public ApiResponse<NotificationDetailResponse> getNotification(
        @ApiIgnore @ModelAttribute("adminMemberId") Long adminMemberId,
        @PathVariable Long notificationId
    ) {
        NotificationDetailVo notificationDetailVo = notificationFacadeService.getNotificationDetail(
            adminMemberId, notificationId);
        return ApiResponse.success(
            notificationAssembler.toNotificationDetailResponse(notificationDetailVo)
        );
    }
}
