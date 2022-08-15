package kr.mashup.branding.ui.qrcode;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import kr.mashup.branding.facade.qrcode.QrCodeService;
import kr.mashup.branding.ui.ApiResponse;
import kr.mashup.branding.ui.qrcode.response.QrCodeResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/qr-code")
public class QrCodeController {

    private final QrCodeService qrCodeService;

    @ApiOperation(
        value = "QR 코드 조회",
        notes =
            "<h2>Error Code</h2>" +
                "<p>" +
                "EVENT_NOT_FOUND</br>" +
                "ATTENDANCE_CODE_NOT_FOUND" +
                "</p>"
    )
    @GetMapping("/{eventId}")
    public ApiResponse<QrCodeResponse> getQrCode(
        @PathVariable Long eventId
    ) {
        final QrCodeResponse res = qrCodeService.getQrCode(eventId);
        return ApiResponse.success(res);
    }
}
