package kr.mashup.branding.infrastructure.data;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import kr.mashup.branding.domain.adminmember.AdminMember;
import kr.mashup.branding.domain.adminmember.AdminMemberRepository;
import kr.mashup.branding.domain.adminmember.Position;
import kr.mashup.branding.domain.applicant.Applicant;
import kr.mashup.branding.domain.applicant.ApplicantRepository;
import kr.mashup.branding.domain.applicant.ApplicantStatus;
import kr.mashup.branding.domain.application.AnswerRequestVo;
import kr.mashup.branding.domain.application.Application;
import kr.mashup.branding.domain.application.ApplicationService;
import kr.mashup.branding.domain.application.CreateApplicationVo;
import kr.mashup.branding.domain.application.UpdateApplicationVo;
import kr.mashup.branding.domain.application.form.ApplicationForm;
import kr.mashup.branding.domain.application.form.ApplicationFormService;
import kr.mashup.branding.domain.application.form.CreateApplicationFormVo;
import kr.mashup.branding.domain.application.form.QuestionRequestVo;
import kr.mashup.branding.domain.application.form.QuestionType;
import kr.mashup.branding.domain.schedule.RecruitmentSchedule;
import kr.mashup.branding.domain.schedule.RecruitmentScheduleRepository;
import kr.mashup.branding.domain.team.CreateTeamVo;
import kr.mashup.branding.domain.team.Team;
import kr.mashup.branding.domain.team.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Profile({"local"})
@Slf4j
@Component
@RequiredArgsConstructor
public class TestDataInitializer {
    private final RecruitmentScheduleRepository recruitmentScheduleRepository;
    private final TeamService teamService;
    private final ApplicationFormService applicationFormService;
    private final ApplicationService applicationService;
    private final ApplicantRepository applicantRepository;
    private final AdminMemberRepository adminMemberRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void initialize() {
        AdminMember adminMember = createAdminMember();
        log.info("AdminMember is created. adminMember: {}", adminMember);

        List<RecruitmentSchedule> recruitmentSchedules = createRecruitmentSchedules();
        log.info("4 RecruitmentSchedules are created. recruitmentSchedules: {}", recruitmentSchedules);

        List<Team> teams = createTeams();
        log.info("6 Teams are created. teams: {}", teams);

        ApplicationForm applicationForm = createApplicationForm(
            adminMember.getAdminMemberId(),
            teams.stream()
                .filter(it -> it.getName().equals("Spring"))
                .findFirst()
                .get()
        );
        log.info("ApplicationForm is created. applicationForm: {}", applicationForm);

        try {
            Applicant applicant = createApplicant();
            log.info("Applicant is created. applicant: {}", applicant);

            Application application = createApplication(applicant.getApplicantId(), applicationForm);
            log.info("Application is created. application: {}", application);
        } catch (Exception e) {
            log.info("Failed to created application for test data. message: {}", e.getMessage(), e);
        }
    }

    private List<RecruitmentSchedule> createRecruitmentSchedules() {
        return recruitmentScheduleRepository.saveAll(RecruitmentSchedule.get12thRecruitSchedules());
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

    private ApplicationForm createApplicationForm(Long adminMemberId, Team team) {
        // 스프링팀 11기 지원서
        return applicationFormService.create(adminMemberId, CreateApplicationFormVo.of(
            team.getTeamId(),
            Arrays.asList(
                QuestionRequestVo.of(
                    "\uD83D\uDCAC 경험을 바탕으로 자기 소개 및 지원 동기에 대해 말씀해주세요. (최소 300자)",
                    300,
                    "",
                    true,
                    QuestionType.MULTI_LINE_TEXT
                ),
                QuestionRequestVo.of(
                    "🧐 경력 혹은 활동사항이나 프로젝트 경험이 있다면 자유롭게 말씀해주세요. (최소 400자)",
                    400,
                    "",
                    true,
                    QuestionType.MULTI_LINE_TEXT
                ),
                QuestionRequestVo.of(
                    "\uD83D\uDC40 주변 사람들이 말하는 당신은 어떤 사람인지 말씀해주세요. (최소 300자)",
                    300,
                    "",
                    true,
                    QuestionType.MULTI_LINE_TEXT
                ),
                QuestionRequestVo.of(
                    "✨ 동아리에 기여해줄 수 있는 부분이 있으면 말씀해주세요. (최소 200자)",
                    200,
                    "",
                    true,
                    QuestionType.MULTI_LINE_TEXT
                ),
                QuestionRequestVo.of(
                    "\uD83E\uDD70 향후 Mash-Up에서 하고 싶은 활동이나 기대되는 것이 있다면 말씀해주세요. (최소 200자)",
                    200,
                    "",
                    true,
                    QuestionType.MULTI_LINE_TEXT
                ),
                QuestionRequestVo.of(
                    "\uD83D\uDDA5 GitHub 혹은 블로그 주소가 있다면 알려주세요.",
                    null,
                    "",
                    true,
                    QuestionType.SINGLE_LINE_TEXT
                )
            ),
            "스프링팀 11기 지원서"
        ));
    }

    private Applicant createApplicant() throws Exception {
        Constructor<Applicant> declaredConstructor = Applicant.class.getDeclaredConstructor();
        declaredConstructor.setAccessible(true);
        Applicant applicant = declaredConstructor.newInstance();
        setApplicantField(String.class, "email", applicant, "mashup.12th.branding.server.dev@gmail.com");
        setApplicantField(String.class, "googleUserId", applicant, "googleUserId");
        setApplicantField(String.class, "name", applicant, "TESTER");
        setApplicantField(String.class, "phoneNumber", applicant, "01031280428");
        setApplicantField(ApplicantStatus.class, "status", applicant, ApplicantStatus.ACTIVE);
        return applicantRepository.save(applicant);
    }

    private void setApplicantField(Class<?> fieldClass, String fieldName, Applicant target, Object value) {
        Field field = ReflectionUtils.findField(Applicant.class, fieldName, fieldClass);
        ReflectionUtils.makeAccessible(Objects.requireNonNull(field));
        ReflectionUtils.setField(field, target, value);
    }

    private AdminMember createAdminMember() {
        AdminMember testadmin = AdminMember.of(
            "testadmin",
            "$2a$10$ReFbOONqzqSbJmEOq9DC0ezs64sfLJumeqei96Ov4Fb8RhVc2Fmf6",
            "01097944578",
            Position.BRANDING_MEMBER
        );
        return adminMemberRepository.save(testadmin);
    }

    // 테스트용 지원서 생성 (사용하려면 지원기간 validate 해제 해야함)
    private Application createApplication(Long applicantId, ApplicationForm applicationForm) {
        Application application = applicationService.create(applicantId,
            new CreateApplicationVo(applicationForm.getTeam().getTeamId()));
        List<AnswerRequestVo> answerRequestVos = application.getAnswers().stream()
            .map(it -> AnswerRequestVo.of(it.getAnswerId(), it.getQuestion().getQuestionId(), "응답"))
            .collect(Collectors.toList());

        UpdateApplicationVo updateApplicationVo = UpdateApplicationVo.of("이름", "01000000000",
            LocalDate.now(), "매쉬업", "서울", answerRequestVos, true);
        applicationService.update(applicantId, application.getApplicationId(), updateApplicationVo);
        return application;
    }
}
