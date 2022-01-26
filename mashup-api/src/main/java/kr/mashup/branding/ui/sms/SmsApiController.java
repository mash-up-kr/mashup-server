package kr.mashup.branding.ui.sms;

import io.swagger.annotations.ApiOperation;
import kr.mashup.branding.facade.SmsFacadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/sms")
public class SmsApiController {

    private final SmsFacadeService smsFacadeService;

    @ApiOperation("Sms 메세지 발송")
    @PostMapping("/send")
    public void sendSms() {
        smsFacadeService.sendSms();
    }

    @ApiOperation("Sms 요청 그룹 리스트")
    @GetMapping("/request-group")
    public void getSmsRequestGroups() {
        smsFacadeService.getAllRequestGroup();
    }

    @ApiOperation("Sms 요청 그룹 조회")
    @GetMapping("/request-group/{requestGroupId}")
    public void getSmsRequests(
            @PathVariable Long requestGroupId
    ) {
        smsFacadeService.getSmsRequests(requestGroupId);
    }

    @ApiOperation("Sms 요청 그룹 새로고침")
    @GetMapping("/request-group/{requestGroupId}/refresh")
    public void refreshSmsRequestGroup() {
        smsFacadeService.refreshRequestGroup();
    }

    @ApiOperation("Sms 요청 그룹 실패한 유저 대상 재시도")
    @GetMapping("/request-group/{requestGroupId}/retry")
    public void retrySendSms() {
        smsFacadeService.retrySendSms();
    }
}
