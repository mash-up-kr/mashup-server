package kr.mashup.branding.facade.application;


import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.adminmember.entity.AdminMember;
import kr.mashup.branding.domain.adminmember.entity.Position;
import kr.mashup.branding.domain.applicant.Applicant;
import kr.mashup.branding.domain.application.Answer;
import kr.mashup.branding.domain.application.Application;
import kr.mashup.branding.domain.application.ApplicationQueryVo;
import kr.mashup.branding.domain.application.form.Question;
import kr.mashup.branding.domain.exception.UnauthorizedException;
import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.team.Team;
import kr.mashup.branding.service.adminmember.AdminMemberService;
import kr.mashup.branding.service.application.ApplicationService;
import kr.mashup.branding.service.generation.GenerationService;
import kr.mashup.branding.service.team.TeamService;
import kr.mashup.branding.util.ExcelGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminApplicationExcelFacadeService {

    private final AdminMemberService adminMemberService;
    private final ApplicationService applicationService;
    private final GenerationService generationService;
    private final TeamService teamService;

    public ByteArrayResource generateApplicationExcel(Long adminMemberId) {
        List<Application> applications = getApplicationsByTeam(adminMemberId);

        if (applications.isEmpty()) {
            throw new IllegalArgumentException("No applications found");
        }

        List<Question> questions = getSortedQuestions(applications.get(0));
        List<String> headers = getHeaders(questions);

        return ExcelGenerator.generate(
            "지원자 목록",
            headers,
            applications,
            application -> getRowData(application, questions)
        );
    }

    private List<Application> getApplicationsByTeam(Long adminMemberId) {
        AdminMember adminMember = adminMemberService.getByAdminMemberId(adminMemberId);
        Position.Team team = getAdminMemberTeam(adminMember);
        Generation generation = generationService.getLatestGeneration();
        Team teamEntity = findTeamByNameAndGeneration(team, generation);

        return applicationService.getApplications(
            adminMember.getAdminMemberId(),
            generation,
            ApplicationQueryVo.of(null, teamEntity.getTeamId(), null, null, null, null, PageRequest.of(0, Integer.MAX_VALUE, Sort.by(Sort.Direction.DESC, "submittedAt")))
        ).getContent();
    }

    private Position.Team getAdminMemberTeam(AdminMember adminMember) {
        return Arrays.stream(adminMember.getPosition().getAuthorities())
            .findFirst()
            .orElseThrow(() -> new UnauthorizedException(ResultCode.ADMIN_MEMBER_NO_ACCESS_TEAM));
    }

    private Team findTeamByNameAndGeneration(Position.Team team, Generation generation) {
        return teamService.findAllTeamsByGeneration(generation)
            .stream()
            .filter(t -> t.getName().equals(team.getName()))
            .findFirst()
            .orElseThrow(() -> new UnauthorizedException(ResultCode.ADMIN_MEMBER_NO_ACCESS_TEAM));
    }

    private List<Question> getSortedQuestions(Application application) {
        return application.getApplicationForm()
            .getQuestions()
            .stream()
            .sorted(Comparator.comparing(Question::getQuestionId))
            .collect(Collectors.toList());
    }

    private List<String> getHeaders(List<Question> questions) {
        List<String> headers = new ArrayList<>();
        headers.add("이름");
        headers.add("소속");
        headers.add("거주지");
        headers.addAll(questions.stream()
            .map(Question::getContent)
            .collect(Collectors.toList()));
        return headers;
    }

    private List<String> getRowData(Application application, List<Question> questions) {
        List<String> rowData = new ArrayList<>();
        Applicant applicant = application.getApplicant();

        // 기본 정보
        rowData.add(applicant.getName());
        rowData.add(applicant.getDepartment());
        rowData.add(applicant.getResidence());

        // 답변 매핑
        Map<Long, String> answerMap = application.getAnswers().stream()
            .collect(Collectors.toMap(
                answer -> answer.getQuestion().getQuestionId(),
                Answer::getContent
            ));

        // 질문 순서대로 답변 추가
        questions.forEach(question ->
            rowData.add(answerMap.getOrDefault(question.getQuestionId(), ""))
        );

        return rowData;
    }
}
