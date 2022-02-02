package kr.mashup.branding.ui.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
}
