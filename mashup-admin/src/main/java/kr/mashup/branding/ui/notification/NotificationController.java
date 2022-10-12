package kr.mashup.branding.ui.notification;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.mashup.branding.EmptyResponse;
import kr.mashup.branding.facade.notification.NotificationFacadeService;
import kr.mashup.branding.ui.ApiResponse;
import kr.mashup.branding.ui.notification.vo.NotificationDetailResponse;
import kr.mashup.branding.ui.notification.vo.NotificationSimpleResponse;
import kr.mashup.branding.ui.notification.vo.SmsSendRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Api(tags = "문자 발송 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    private final NotificationFacadeService notificationFacadeService;

    // 디자인 상 SMS 발송 -> 발송 내역으로 이동하시겠습니까? -> 발송 목록 조회 순.
    // 굳이 응답 결과를 NotificationDetailResponse 로 조립해서 보낼 필요 없다.
    @ApiOperation("SMS 발송")
    @PostMapping("/sms/send")
    public ApiResponse<EmptyResponse> sendSms(
        @ApiIgnore @ModelAttribute("adminMemberId") Long adminMemberId,
        @RequestBody SmsSendRequest smsSendRequest
    ) {
        notificationFacadeService.createSmsNotification(adminMemberId, smsSendRequest);

        return ApiResponse.success(EmptyResponse.of());
    }


    @ApiOperation("발송내역 목록조회")
    @GetMapping
    public ApiResponse<List<NotificationSimpleResponse>> getNotifications(
        @ApiIgnore @ModelAttribute("adminMemberId") Long adminMemberId,
        @RequestParam(required = false) String searchWord,
        Pageable pageable
    ) {
        final Page<NotificationSimpleResponse> responses
            = notificationFacadeService.getNotifications(adminMemberId, searchWord, pageable);

        return ApiResponse.success(responses);
    }

    @ApiOperation("발송내역 상세조회")
    @GetMapping("/{notificationId}")
    public ApiResponse<NotificationDetailResponse> getNotification(
        @ApiIgnore @ModelAttribute("adminMemberId") Long adminMemberId,
        @PathVariable Long notificationId
    ) {
        final NotificationDetailResponse response
            = notificationFacadeService.getNotificationDetail(adminMemberId, notificationId);

        return ApiResponse.success(response);
    }
}
