package kr.mashup.branding.ui.emailnotification;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.mashup.branding.EmptyResponse;
import kr.mashup.branding.facade.emailnotification.EmailNotificationFacadeService;
import kr.mashup.branding.ui.ApiResponse;
import kr.mashup.branding.ui.emailnotification.vo.EmailNotificationDetailResponseVo;
import kr.mashup.branding.ui.emailnotification.vo.EmailNotificationResponseVo;
import kr.mashup.branding.ui.emailnotification.vo.EmailRequestResponseVo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Api(tags = "문자 발송 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/email")
public class EmailNotificationController {

    private final EmailNotificationFacadeService emailNotificationFacadeService;

    @ApiOperation("Email 발송")
    @PostMapping("/send")
    public ApiResponse<EmptyResponse> sendSms(
        @ApiIgnore @ModelAttribute("adminMemberId") final Long adminMemberId,
        @RequestParam(defaultValue = "13", required = false) final  Integer generationNumber,
        @RequestBody final EmailSendRequest emailSendRequest
    ) {
        emailNotificationFacadeService.sendEmailNotification(adminMemberId, generationNumber, emailSendRequest);

        return ApiResponse.success(EmptyResponse.of());
    }

    @ApiOperation("이메일 발송내역 목록 조회")
    @GetMapping("")
    public ApiResponse<List<EmailNotificationResponseVo>> readEmailNotifications(
            @RequestParam(required = false) String searchWord, Pageable pageable) {
        Page<EmailNotificationResponseVo> data = emailNotificationFacadeService.readEmailNotifications(searchWord, pageable);

        return ApiResponse.success(data);
    }

    @ApiOperation("이메일 발송 내역 상세 조회")
    @GetMapping("/{emailNotificationId}")
    public ApiResponse<EmailNotificationDetailResponseVo> readEmailNotification(@PathVariable Long emailNotificationId) {
        EmailNotificationDetailResponseVo data = emailNotificationFacadeService.readEmailNotification(emailNotificationId);

        return ApiResponse.success(data);
    }
}
