package kr.mashup.branding.ui.popup;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import kr.mashup.branding.domain.popup.PopupType;
import kr.mashup.branding.facade.popup.MemberPopupFacadeService;
import kr.mashup.branding.security.MemberAuth;
import kr.mashup.branding.ui.ApiResponse;
import kr.mashup.branding.ui.EmptyResponse;
import lombok.RequiredArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

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
		return ApiResponse.success(memberPopupFacadeService.getEnabledPopupTypes(memberAuth.getMemberGenerationId()));
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
}
