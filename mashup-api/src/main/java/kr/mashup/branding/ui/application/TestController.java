package kr.mashup.branding.ui.application;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.mashup.branding.domain.application.ApplicationService;
import kr.mashup.branding.ui.ApiResponse;
import lombok.RequiredArgsConstructor;

@Profile("!production")
@RestController
@RequestMapping("/api/v1/test/applications")
@RequiredArgsConstructor
public class TestController {
    private final ApplicationService applicationService;

    /**
     * 개발서버에서 지원서 삭제
     */
    @DeleteMapping("/{applicationId}")
    public ApiResponse<?> delete(
        @PathVariable Long applicationId
    ) {
        applicationService.delete(applicationId);
        return ApiResponse.success();
    }
}
