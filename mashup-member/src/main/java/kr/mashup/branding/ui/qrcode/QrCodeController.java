package kr.mashup.branding.ui.qrcode;

import io.swagger.annotations.ApiOperation;
import kr.mashup.branding.facade.qrcode.QrCodeService;
import kr.mashup.branding.ui.ApiResponse;
import kr.mashup.branding.ui.qrcode.request.QrCreateRequest;
import kr.mashup.branding.ui.qrcode.response.QrCheckResponse;
import kr.mashup.branding.ui.qrcode.response.QrCreateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/qr-code")
public class QrCodeController {

    private final QrCodeService qrCodeService;

    @ApiOperation("QR 코드 생성")
    @PostMapping("/")
    public ApiResponse<QrCreateResponse> create(
        @RequestBody QrCreateRequest request
    ) {
        QrCreateResponse res = qrCodeService.generate(request);

        return ApiResponse.success(res);
    }

    @ApiOperation("QR 코드 체크")
    @GetMapping("/")
    public ApiResponse<QrCheckResponse> valid(
        @RequestParam Long eventId,
        @RequestParam String code
    ) {
        QrCheckResponse res =
            qrCodeService.isAvailableCode(eventId, code, LocalDateTime.now());

        return ApiResponse.success(res);
    }

}
