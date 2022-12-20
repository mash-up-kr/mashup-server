package kr.mashup.branding.ui.emailnotification;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.mashup.branding.EmptyResponse;
import kr.mashup.branding.facade.emailnotification.EmailNotificationFacadeService;
import kr.mashup.branding.ui.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "문자 발송 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/email")
public class EmailNotificationController {

    private final EmailNotificationFacadeService emailNotificationFacadeService;

    @ApiOperation("Email 발송")
    @PostMapping("/send")
    public ApiResponse<EmptyResponse> sendSms(
        @ApiIgnore @ModelAttribute("adminMemberId") Long adminMemberId,
        @RequestParam(defaultValue = "13", required = false) Integer generationNumber,
        @RequestBody EmailSendRequest emailSendRequest
    ) {
        emailNotificationFacadeService.sendEmailNotification(adminMemberId, generationNumber, emailSendRequest);

        return ApiResponse.success(EmptyResponse.of());
    }


}
