package kr.mashup.branding.ui.pushhistory;

import kr.mashup.branding.facade.pushhistory.PushHistoryFacadeService;
import kr.mashup.branding.security.MemberAuth;
import kr.mashup.branding.ui.ApiResponse;
import kr.mashup.branding.ui.pushhistory.response.PushHistoriesResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/v1/push-histories")
@RequiredArgsConstructor
public class PushHistoryController {

    private final PushHistoryFacadeService pushHistoryFacadeService;

    @PostMapping
    public ApiResponse<PushHistoriesResponse> getPushHistoriesWithCheck(
            @ApiIgnore MemberAuth memberAuth,
            @PageableDefault Pageable pageable){

        final PushHistoriesResponse response =
                pushHistoryFacadeService.getPushHistoriesAndUpdateCheckTime(memberAuth.getMemberId(), pageable);

        return ApiResponse.success(response);
    }

    @GetMapping
    public ApiResponse<PushHistoriesResponse> getPushHistories(
            @ApiIgnore MemberAuth memberAuth,
            @PageableDefault Pageable pageable){

        final PushHistoriesResponse response =
                pushHistoryFacadeService.getPushHistories(memberAuth.getMemberId(), pageable);

        return ApiResponse.success(response);
    }
}
