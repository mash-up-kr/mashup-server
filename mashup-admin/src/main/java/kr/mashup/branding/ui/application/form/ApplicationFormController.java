package kr.mashup.branding.ui.application.form;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
        @PathVariable Long teamId
    ) {
        return applicationFormFacadeService.getApplicationForms(teamId)
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
}
