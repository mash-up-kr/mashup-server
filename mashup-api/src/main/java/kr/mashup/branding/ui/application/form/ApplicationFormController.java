package kr.mashup.branding.ui.application.form;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import kr.mashup.branding.facade.application.form.ApplicationFormFacadeService;
import lombok.RequiredArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/v1/teams/{teamId}/application-forms")
@RequiredArgsConstructor
public class ApplicationFormController {
    private final ApplicationFormFacadeService applicationFormFacadeService;
    private final ApplicationFormAssembler applicationFormAssembler;

    @ApiOperation("지원서 목록 조회")
    @GetMapping
    public List<ApplicationFormResponse> getApplications(
        @ApiIgnore @ModelAttribute("applicantId") Long applicantId,
        @PathVariable Long teamId
    ) {
        return applicationFormFacadeService.getByTeamId(teamId)
            .stream()
            .map(applicationFormAssembler::toApplicationFormResponse)
            .collect(Collectors.toList());
    }
}
