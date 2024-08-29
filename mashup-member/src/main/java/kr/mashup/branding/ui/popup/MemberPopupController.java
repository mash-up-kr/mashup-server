package kr.mashup.branding.ui.popup;

import io.swagger.annotations.ApiOperation;
import kr.mashup.branding.domain.popup.PopupType;
import kr.mashup.branding.facade.popup.MemberPopupFacadeService;
import kr.mashup.branding.security.MemberAuth;
import kr.mashup.branding.ui.ApiResponse;
import kr.mashup.branding.ui.EmptyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@RequestMapping("api/v1/member-popups")
@RequiredArgsConstructor
public class MemberPopupController {

    private final MemberPopupFacadeService memberPopupFacadeService;

    @ApiOperation(
        value = "멤버가 볼 수 있는 팝업 조회",
        notes =
            "<h2>Error Code</h2>" +
                "<p>" +
                "MEMBER_GENERATION_NOT_FOUND</br>" +
                "STORAGE_NOT_FOUND</br>" +
                "</p>"

    )
    @GetMapping
    public ApiResponse<List<PopupType>> getEnabledPopupTypes(
        @ApiIgnore MemberAuth memberAuth
    ) {
        return ApiResponse.success(memberPopupFacadeService.getEnabledPopupTypes(memberAuth));
    }

    @ApiOperation(
        value = "팝업 비활성화",
        notes =
            "<h2>Error Code</h2>" +
                "<p>" +
                "MEMBER_GENERATION_NOT_FOUND</br>" +
                "STORAGE_NOT_FOUND</br>" +
                "INACTIVE_GENERATION</br>" +
                "</p>"

    )
    @PatchMapping("/{popupType}/disabled")
    public ApiResponse<EmptyResponse> updateDisabled(
        @ApiIgnore MemberAuth memberAuth,
        @PathVariable PopupType popupType
    ) {
        memberPopupFacadeService.updateDisabled(memberAuth.getMemberGenerationId(), popupType);
        return ApiResponse.success();
    }

    @ApiOperation(
        value = "팝업 마지막 본 시간 업데이트",
        notes =
            "<h2>Error Code</h2>" +
                "<p>" +
                "MEMBER_GENERATION_NOT_FOUND</br>" +
                "STORAGE_NOT_FOUND</br>" +
                "INACTIVE_GENERATION</br>" +
                "</p>"

    )
    @PatchMapping("/{popupType}/last-viewed")
    public ApiResponse<EmptyResponse> updateLastViewedAt(
        @ApiIgnore MemberAuth memberAuth,
        @PathVariable PopupType popupType
    ) {
        memberPopupFacadeService.updateLastViewedAt(memberAuth.getMemberGenerationId(), popupType);
        return ApiResponse.success();
    }

    @ApiOperation(value = "팝업 초기화(개발용)")
    @DeleteMapping
    public ApiResponse<EmptyResponse> delete(
        @ApiIgnore MemberAuth memberAuth,
        @ApiIgnore @Value("${spring.profiles.active}") String activeProfile
    ) {
        if (!activeProfile.equals("production")) {
            memberPopupFacadeService.deleteMemberPopup(memberAuth.getMemberId());
        }
        return ApiResponse.success();
    }
}
