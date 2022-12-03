package kr.mashup.branding.ui.qrcode;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import kr.mashup.branding.facade.qrcode.QrCodeFacadeService;
import kr.mashup.branding.ui.ApiResponse;
import kr.mashup.branding.ui.qrcode.request.QrCreateRequest;
import kr.mashup.branding.ui.qrcode.response.QrCreateResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/qr-code")
public class QrCodeController {

    private final QrCodeFacadeService qrCodeFacadeService;

    @ApiOperation("QR 코드 생성")
    @PostMapping("/")
    public ApiResponse<QrCreateResponse> create(
        @RequestBody QrCreateRequest request
    ) {
        final QrCreateResponse res = qrCodeFacadeService.generate(request);
        return ApiResponse.success(res);
    }
}
