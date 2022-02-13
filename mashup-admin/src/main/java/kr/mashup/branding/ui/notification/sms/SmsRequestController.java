package kr.mashup.branding.ui.notification.sms;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import kr.mashup.branding.facade.notification.sms.SmsRequestFacadeService;
import kr.mashup.branding.ui.ApiResponse;
import lombok.RequiredArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

@RequestMapping("/api/v1/applicants/{applicantId}/sms-requests")
@RestController
@RequiredArgsConstructor
public class SmsRequestController {
    private final SmsRequestFacadeService smsRequestFacadeService;
    private final SmsRequestAssembler smsRequestAssembler;

    @ApiOperation("문자 발송내역 목록조회")
    @GetMapping
    public ApiResponse<List<SmsRequestDetailResponse>> getNotificationSmsRequests(
        @ApiIgnore @ModelAttribute("adminMemberId") Long adminMemberId,
        @RequestParam Long applicantId
    ) {
        return ApiResponse.success(
            smsRequestFacadeService.getSmsRequests(adminMemberId, applicantId)
                .stream()
                .map(smsRequestAssembler::toSmsRequestDetailResponse)
                .collect(Collectors.toList())
        );
    }
}
