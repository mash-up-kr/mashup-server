package kr.mashup.branding.ui.application;

import io.swagger.annotations.ApiOperation;
import kr.mashup.branding.domain.application.Application;
import kr.mashup.branding.domain.application.CreateApplicationVo;
import kr.mashup.branding.facade.application.ApplicationFacadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/applications")
@RequiredArgsConstructor
public class ApplicationController {
    private final ApplicationFacadeService applicationFacadeService;

    /**
     * 팀 id(or name) 받아서 만들기
     * 새로만들면 201, 있던거면 200
     */
    @ApiOperation("내 지원서 생성")
    @PostMapping
    public ApplicationResponse create(
            @RequestBody CreateApplicationRequest createApplicationRequest
    ) {
        Application application = applicationFacadeService.create(toCreateApplicationVo(createApplicationRequest));
        return toApplicationResponse(application);
    }

    private CreateApplicationVo toCreateApplicationVo(CreateApplicationRequest createApplicationRequest) {
        Assert.notNull(createApplicationRequest, "'createApplicationRequest' must not be null");
        return new CreateApplicationVo(createApplicationRequest.getTeamId());
    }

    private ApplicationResponse toApplicationResponse(Application application) {
        Assert.notNull(application, "'application' must not be null");
        return new ApplicationResponse(application.getApplicationId());
    }

    /**
     * 임시저장
     * 이미 저장된 다른팀 지원서가 있으면 삭제
     */
    @ApiOperation("내 지원서 임시 저장")
    @PutMapping("/{applicationId}")
    public ApplicationResponse update(
            @RequestBody ApplicationUpdateRequest applicationUpdateRequest
    ) {
        return null;
    }

    /**
     * 지원서가 없으면? 오류
     * 임시저장이면 성공
     * 이미 제출했어도 성공
     */
    @ApiOperation("내 지원서 제출")
    @PostMapping("/{applicationId}/submit")
    public ApplicationResponse submit(
            @PathVariable Long applicationId
    ) {
        return null;
    }

    @ApiOperation("내 지원서 목록 조회")
    @GetMapping
    public List<ApplicationResponse> getApplications() {
        return Collections.emptyList();
    }

    @ApiOperation("내 지원서 상세 조회")
    @GetMapping("/{applicationId}")
    public ApplicationResponse getApplication() {
        return null;
    }
}
