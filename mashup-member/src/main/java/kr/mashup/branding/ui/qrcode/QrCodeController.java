package kr.mashup.branding.ui.qrcode;

import io.swagger.annotations.ApiOperation;
import kr.mashup.branding.facade.qrcode.QrCodeService;
import kr.mashup.branding.ui.ApiResponse;
import kr.mashup.branding.ui.qrcode.request.QrCreateRequest;
import kr.mashup.branding.ui.qrcode.response.QrCodeResponse;
import kr.mashup.branding.ui.qrcode.response.QrCreateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
        final QrCreateResponse res = qrCodeService.generate(request);
        return ApiResponse.success(res);
    }

    @ApiOperation("QR 코드 조회")
    @GetMapping("/{eventId}")
    public ApiResponse<QrCodeResponse> getQrCode(
        @PathVariable Long eventId
    ) {
        final QrCodeResponse res = qrCodeService.getQrCode(eventId);
        return ApiResponse.success(res);
    }
}
