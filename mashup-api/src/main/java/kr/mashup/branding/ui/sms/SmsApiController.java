package kr.mashup.branding.ui.sms;

import io.swagger.annotations.ApiOperation;
import kr.mashup.branding.facade.SmsFacadeService;
import kr.mashup.branding.ui.sms.dto.SmsRequestGroupResponse;
import kr.mashup.branding.ui.sms.dto.SmsRequestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/sms")
public class SmsApiController {

    private final SmsFacadeService smsFacadeService;

    @ApiOperation("Sms 메세지 발송")
    @PostMapping("/send")
    public Boolean sendSms() {
        smsFacadeService.sendSms();
        return true;
    }

    @ApiOperation("Sms 요청 그룹 리스트")
    @GetMapping("/request-group")
    public List<SmsRequestGroupResponse> getSmsRequestGroups() {
        return smsFacadeService.getAllRequestGroup();
    }

    @ApiOperation("Sms 요청 그룹 조회")
    @GetMapping("/request-group/{requestGroupId}")
    public List<SmsRequestResponse> getSmsRequests(
            @PathVariable Long requestGroupId
    ) {
        return smsFacadeService.getSmsRequests(requestGroupId);
    }

    @ApiOperation("Sms 요청 그룹 새로고침")
    @PostMapping("/request-group/{requestGroupId}/refresh")
    public void refreshSmsRequestGroup() {
        smsFacadeService.refreshRequestGroup();
    }

    @ApiOperation("Sms 요청 그룹 실패한 유저 대상 재시도")
    @PostMapping("/request-group/{requestGroupId}/retry")
    public void retrySendSms() {
        smsFacadeService.retrySendSms();
    }
}
