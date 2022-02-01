package kr.mashup.branding.ui.application.form;

import java.util.List;

import kr.mashup.branding.ui.team.TeamResponse;
import lombok.Data;

@Data
public class ApplicationFormResponse {
    private final Long applicationFormId;
    private final TeamResponse team;
    private final List<QuestionResponse> questions;
    private final String name;
}
