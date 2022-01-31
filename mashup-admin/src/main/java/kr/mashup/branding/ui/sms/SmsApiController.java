package kr.mashup.branding.ui.sms;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.mashup.branding.facade.SmsFacadeService;
import kr.mashup.branding.ui.sms.dto.SmsRequestGroupResponse;
import kr.mashup.branding.ui.sms.dto.SmsRequestResponse;
import kr.mashup.branding.ui.sms.dto.SmsSendRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "문자 발송 관련 API (아직 구현되지 않음)")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/sms")
public class SmsApiController {

    private final SmsFacadeService smsFacadeService;

    @ApiOperation("Sms 메세지 발송")
    @PostMapping("/send")
    public Boolean sendSms(
            @RequestBody SmsSendRequest request
    ) {
//        smsFacadeService.sendSms(request);
//        return true;
        throw new RuntimeException("아직 구현되지 않음");
    }

    @ApiOperation("Sms 요청 그룹 리스트")
    @GetMapping("/request-group")
    public List<SmsRequestGroupResponse> getSmsRequestGroups() {
//        return smsFacadeService.getAllRequestGroup();
        throw new RuntimeException("아직 구현되지 않음");
    }

    @ApiOperation("Sms 요청 그룹 조회")
    @GetMapping("/request-group/{requestGroupId}")
    public List<SmsRequestResponse> getSmsRequests(
            @PathVariable Long requestGroupId
    ) {
//        return smsFacadeService.getSmsRequests(requestGroupId);
        throw new RuntimeException("아직 구현되지 않음");
    }

    @ApiOperation("Sms 요청 그룹 새로고침")
    @PostMapping("/request-group/{requestGroupId}/refresh")
    public void refreshSmsRequestGroup(
            @PathVariable Long requestGroupId
    ) {
//        smsFacadeService.refreshRequestGroup();
        throw new RuntimeException("아직 구현되지 않음");
    }

    @ApiOperation("Sms 요청 그룹 실패한 유저 대상 재시도")
    @PostMapping("/request-group/{requestGroupId}/retry")
    public void retrySendSms(
            @PathVariable Long requestGroupId
    ) {
//        smsFacadeService.retrySendSms();
        throw new RuntimeException("아직 구현되지 않음");
    }
}
