package kr.mashup.branding.ui.application.form;

import java.util.Collections;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/application-forms")
public class ApplicationFormController {
    @GetMapping
    public List<ApplicationFormResponse> getLists() {
        return Collections.emptyList();
    }
}
