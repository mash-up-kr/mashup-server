package kr.mashup.branding.ui.application.form;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import kr.mashup.branding.domain.application.form.ApplicationForm;
import kr.mashup.branding.domain.application.form.CreateApplicationFormVo;
import kr.mashup.branding.facade.application.form.ApplicationFormFacadeService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/teams/{teamId}/application-forms")
@RequiredArgsConstructor
public class ApplicationFormController {
    private final ApplicationFormFacadeService applicationFormFacadeService;
    private final ApplicationFormAssembler applicationFormAssembler;

    @GetMapping
    public List<ApplicationFormResponse> getApplicationForms(
        @PathVariable Long teamId,
        @RequestParam(required = false) String name,
        Pageable pageable
    ) {
        return applicationFormFacadeService.getApplicationForms(teamId, name, pageable)
            .stream()
            .map(applicationFormAssembler::toApplicationFormResponse)
            .collect(Collectors.toList());
    }

    @PostMapping
    public ApplicationFormResponse create(
        @PathVariable Long teamId,
        @RequestBody CreateApplicationFormRequest createApplicationFormRequest
    ) {
        CreateApplicationFormVo createApplicationFormVo = applicationFormAssembler.toCreateApplicationFormVo(teamId,
            createApplicationFormRequest);
        ApplicationForm applicationForm = applicationFormFacadeService.create(createApplicationFormVo);
        return applicationFormAssembler.toApplicationFormResponse(applicationForm);
    }

    @PutMapping("/{applicationFormId}")
    public ApplicationFormResponse update(
        @PathVariable Long teamId,
        @PathVariable Long applicationFormId,
        @RequestBody UpdateApplicationFormRequest updateApplicationFormRequest
    ) {
        ApplicationForm applicationForm = applicationFormFacadeService.update(
            teamId,
            applicationFormId,
            applicationFormAssembler.toUpdateApplicationFormVo(updateApplicationFormRequest)
        );
        return applicationFormAssembler.toApplicationFormResponse(applicationForm);
    }

    @DeleteMapping("/{applicationFormId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
        @PathVariable Long teamId,
        @PathVariable Long applicationFormId
    ) {
        applicationFormFacadeService.delete(teamId, applicationFormId);
    }
}
