package kr.mashup.branding.infrastructure.data;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import kr.mashup.branding.domain.applicant.Applicant;
import kr.mashup.branding.domain.applicant.ApplicantRepository;
import kr.mashup.branding.domain.application.form.ApplicationForm;
import kr.mashup.branding.domain.application.form.ApplicationFormService;
import kr.mashup.branding.domain.application.form.CreateApplicationFormVo;
import kr.mashup.branding.domain.application.form.QuestionRequestVo;
import kr.mashup.branding.domain.application.form.QuestionType;
import kr.mashup.branding.domain.team.CreateTeamVo;
import kr.mashup.branding.domain.team.Team;
import kr.mashup.branding.domain.team.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Profile({"local", "develop"})
@Slf4j
@Component
@RequiredArgsConstructor
public class TestDataInitializer {
    private final TeamService teamService;
    private final ApplicationFormService applicationFormService;
    private final ApplicantRepository applicantRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void initialize() {
        List<Team> teams = createTeams();
        log.info("6 Teams are created. teams: {}", teams);

        ApplicationForm applicationForm = createApplicationForm(teams.stream()
            .filter(it -> it.getName().equals("Spring"))
            .findFirst()
            .get()
        );
        log.info("ApplicationForm is created. applicationForm: {}", applicationForm);

        Applicant applicant = createApplicant();
        log.info("Applicant is created. applicant: {}", applicant);
    }

    private List<Team> createTeams() {
        teamService.create(CreateTeamVo.of("Design"));
        teamService.create(CreateTeamVo.of("Web"));
        teamService.create(CreateTeamVo.of("Android"));
        teamService.create(CreateTeamVo.of("iOS"));
        teamService.create(CreateTeamVo.of("Node"));
        teamService.create(CreateTeamVo.of("Spring"));
        return teamService.findAllTeams();
    }

    private ApplicationForm createApplicationForm(Team team) {
        // 스프링팀 11기 지원서
        return applicationFormService.create(CreateApplicationFormVo.of(
            team.getTeamId(),
            Arrays.asList(
                QuestionRequestVo.of(
                    "\uD83D\uDCAC 경험을 바탕으로 자기 소개 및 지원 동기에 대해 말씀해주세요. (최소 300자)",
                    300,
                    true,
                    QuestionType.MULTI_LINE_TEXT
                ),
                QuestionRequestVo.of(
                    "🧐 경력 혹은 활동사항이나 프로젝트 경험이 있다면 자유롭게 말씀해주세요. (최소 400자)",
                    400,
                    true,
                    QuestionType.MULTI_LINE_TEXT
                ),
                QuestionRequestVo.of(
                    "\uD83D\uDC40 주변 사람들이 말하는 당신은 어떤 사람인지 말씀해주세요. (최소 300자)",
                    300,
                    true,
                    QuestionType.MULTI_LINE_TEXT
                ),
                QuestionRequestVo.of(
                    "✨ 동아리에 기여해줄 수 있는 부분이 있으면 말씀해주세요. (최소 200자)",
                    200,
                    true,
                    QuestionType.MULTI_LINE_TEXT
                ),
                QuestionRequestVo.of(
                    "\uD83E\uDD70 향후 Mash-Up에서 하고 싶은 활동이나 기대되는 것이 있다면 말씀해주세요. (최소 200자)",
                    200,
                    true,
                    QuestionType.MULTI_LINE_TEXT
                ),
                QuestionRequestVo.of(
                    "\uD83D\uDDA5 GitHub 혹은 블로그 주소가 있다면 알려주세요.",
                    null,
                    true,
                    QuestionType.SINGLE_LINE_TEXT
                )
            ),
            "스프링팀 11기 지원서"
        ));
    }

    private Applicant createApplicant() {
        Applicant tester = Applicant.tester();
        return applicantRepository.save(tester);
    }
}
