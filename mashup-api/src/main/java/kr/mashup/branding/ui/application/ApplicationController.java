package kr.mashup.branding.ui.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import kr.mashup.branding.domain.application.Application;
import kr.mashup.branding.facade.application.ApplicationFacadeService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/applications")
@RequiredArgsConstructor
public class ApplicationController {
    private final ApplicationFacadeService applicationFacadeService;
    private final ApplicationAssembler applicationAssembler;

    /**
     * 팀 id(or name) 받아서 만들기
     */
    @ApiOperation("내 지원서 생성")
    @PostMapping
    public ApplicationResponse create(
        @RequestBody CreateApplicationRequest createApplicationRequest
    ) {
        Application application = applicationFacadeService.create(
            applicationAssembler.toCreateApplicationVo(createApplicationRequest)
        );
        return applicationAssembler.toApplicationResponse(application);
    }

    /**
     * 임시저장
     * TODO: 이미 저장된 다른팀 지원서가 있으면 삭제
     * TODO: 개인정보 동의여부 필드 필요한지, 저장은 어떻게 할지
     */
    @ApiOperation("내 지원서 임시 저장")
    @PutMapping("/{applicationId}")
    public ApplicationResponse update(
        @PathVariable Long applicationId,
        @RequestBody UpdateApplicationRequest updateApplicationRequest
    ) {
        Application application = applicationFacadeService.update(
            applicationId,
            applicationAssembler.toUpdateApplicationVo(updateApplicationRequest)
        );
        return applicationAssembler.toApplicationResponse(application);
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
        // TODO: applicant
        Long applicantId = 0L;
        Application application = applicationFacadeService.submit(applicationId);
        return applicationAssembler.toApplicationResponse(application);
    }

    @ApiOperation("내 지원서 목록 조회")
    @GetMapping
    public List<ApplicationResponse> getApplications() {
        // TODO: applicant
        Long applicantId = 0L;
        List<Application> applications = applicationFacadeService.getApplications(applicantId);
        return applications.stream()
            .map(applicationAssembler::toApplicationResponse)
            .collect(Collectors.toList());
    }

    @ApiOperation("내 지원서 상세 조회")
    @GetMapping("/{applicationId}")
    public ApplicationResponse getApplication(
        @PathVariable Long applicationId
    ) {
        // TODO: applicant
        Long applicantId = 0L;
        Application application = applicationFacadeService.getApplication(applicantId, applicationId);
        return applicationAssembler.toApplicationResponse(application);
    }
}
