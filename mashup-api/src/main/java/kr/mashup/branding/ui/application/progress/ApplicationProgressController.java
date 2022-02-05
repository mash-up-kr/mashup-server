package kr.mashup.branding.ui.application.progress;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import kr.mashup.branding.domain.application.progress.ApplicationProgress;
import kr.mashup.branding.facade.application.progress.ApplicationProgressFacadeService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/applications")
public class ApplicationProgressController {

    private ApplicationProgressAssembler applicationProgressAssembler;
    private ApplicationProgressFacadeService applicationProgressFacadeService;

    @ApiOperation("진행 상태 조회")
    @GetMapping("/{applicationId}/progress")
    public ApplicationProgressResponse getApplicationProgress(
        @PathVariable Long applicationId
    ) {
        ApplicationProgress applicationProgress = applicationProgressFacadeService
            .getApplicationProgress(applicationId);
        return applicationProgressAssembler.toApplicationProgressResponse(applicationId, applicationProgress);
    }

    @ApiOperation("진행 상태 업데이트")
    @PutMapping("/{applicationId}/progress")
    public ApplicationProgressResponse updateApplicationProgress(
        @PathVariable Long applicationId,
        @RequestBody UpdateApplicationProgressRequest updateApplicationProgressRequest
    ) {
        ApplicationProgress applicationProgress = applicationProgressFacadeService
            .updateApplicationProgress(applicationId, updateApplicationProgressRequest);
        return applicationProgressAssembler.toApplicationProgressResponse(applicationId, applicationProgress);
    }
}
