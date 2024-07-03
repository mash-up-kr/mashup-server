package kr.mashup.branding.ui.rnb;

import kr.mashup.branding.facade.rnb.RnbMetaFacadeService;
import kr.mashup.branding.security.MemberAuth;
import kr.mashup.branding.ui.ApiResponse;
import kr.mashup.branding.ui.rnb.response.RnbMetaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RequiredArgsConstructor
@RequestMapping("/api/v1/meta")
@RestController
public class MetaController {

    private final RnbMetaFacadeService rnbMetaFacadeService;

    @GetMapping("/rnb")
    public ApiResponse<RnbMetaResponse> getByGenerationNumber(@ApiIgnore MemberAuth auth) {
        final RnbMetaResponse response = rnbMetaFacadeService.getRnbMetaData(auth.getMemberId());
        return ApiResponse.success(response);
    }
}
