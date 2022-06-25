package kr.mashup.branding.ui.qrcode;

import kr.mashup.branding.facade.QrCodeService;
import kr.mashup.branding.ui.ApiResponse;
import kr.mashup.branding.ui.qrcode.response.QrCheckResponse;
import kr.mashup.branding.ui.qrcode.request.QrCreateRequest;
import kr.mashup.branding.ui.qrcode.response.QrCreateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/qr-code")
public class QrCodeController {

    private final QrCodeService qrCodeService;

    @PostMapping("/")
    public ApiResponse<QrCreateResponse> create(
        @RequestBody QrCreateRequest request
    ) {
        String url = qrCodeService.generate(
            request.getEventId(),
            request.getCode(),
            request.getStart(),
            request.getEnd()
        );

        return ApiResponse.success(QrCreateResponse.of(url));
    }

    @GetMapping("/")
    public ApiResponse<QrCheckResponse> valid(
        @RequestParam Long eventId,
        @RequestParam String code
    ) {
        boolean result = qrCodeService.isAvailableCode(
            eventId, code, LocalDateTime.now()
        );
        return ApiResponse.success(QrCheckResponse.of(result));
    }

}
