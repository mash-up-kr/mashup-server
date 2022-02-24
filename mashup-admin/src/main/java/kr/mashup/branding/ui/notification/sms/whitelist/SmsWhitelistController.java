package kr.mashup.branding.ui.notification.sms.whitelist;

import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.mashup.branding.domain.notification.sms.whitelist.SmsWhitelistService;
import kr.mashup.branding.ui.ApiResponse;
import lombok.RequiredArgsConstructor;

@Profile("!production")
@RestController
@RequestMapping("/api/v1/notifications/sms/whitelist")
@RequiredArgsConstructor
public class SmsWhitelistController {
    private final SmsWhitelistService smsWhitelistService;

    @GetMapping
    public ApiResponse<List<String>> getAll() {
        return ApiResponse.success(
            smsWhitelistService.getAll()
        );
    }

    @PostMapping("/{phoneNumber}")
    public ApiResponse<String> add(
        @PathVariable String phoneNumber
    ) {
        return ApiResponse.success(
            smsWhitelistService.add(phoneNumber)
        );
    }

    @DeleteMapping("/{phoneNumber}")
    public ApiResponse<?> remove(
        @PathVariable String phoneNumber
    ) {
        smsWhitelistService.remove(phoneNumber);
        return ApiResponse.success();
    }
}
