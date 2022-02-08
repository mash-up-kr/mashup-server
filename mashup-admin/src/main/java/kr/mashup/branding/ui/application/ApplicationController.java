package kr.mashup.branding.ui.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.mashup.branding.domain.application.Application;
import kr.mashup.branding.facade.application.ApplicationFacadeService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/applications")
@RequiredArgsConstructor
public class ApplicationController {
    private final ApplicationFacadeService applicationFacadeService;
    private final ApplicationAssembler applicationAssembler;

    @GetMapping
    public List<ApplicationResponse> getApplications(
        @RequestParam(required = false) String searchWord,
        Pageable pageable
    ) {
        return applicationFacadeService.getApplications(searchWord, pageable)
            .stream()
            .map(applicationAssembler::toApplicationResponse)
            .collect(Collectors.toList());
    }

    @GetMapping("/{applicationId}")
    public ApplicationResponse getApplication(
        @PathVariable Long applicationId
    ) {
        Application application = applicationFacadeService.getApplication(applicationId);
        return applicationAssembler.toApplicationResponse(application);
    }

    /**
     * 여러개의 지원서에 대해서 결과 변경
     */
    @PostMapping("/update-result")
    public List<ApplicationResponse> updateResult(
        @RequestBody UpdateApplicationResultsRequest updateApplicationResultsRequest
    ) {
        return applicationFacadeService.updateResults(
            applicationAssembler.toUpdateApplicationResultsVoList(updateApplicationResultsRequest)
        ).stream()
            .map(applicationAssembler::toApplicationResponse)
            .collect(Collectors.toList());
    }

    /**
     * 지원서의 결과 및 면접일정 변경
     */
    @PostMapping("/{applicationId}/update-result")
    public ApplicationResponse updateResult(
        @PathVariable Long applicationId,
        @RequestBody UpdateApplicationResultRequest updateApplicationResultRequest
    ) {
        Long adminMemberId = 0L;
        Application application = applicationFacadeService.updateResult(
            adminMemberId,
            applicationId,
            applicationAssembler.toUpdateApplicationResultVo(updateApplicationResultRequest)
        );
        return applicationAssembler.toApplicationResponse(application);
    }
}
