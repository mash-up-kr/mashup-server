package kr.mashup.branding.domain.application;

import java.lang.reflect.Constructor;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.test.util.ReflectionTestUtils;

import kr.mashup.branding.domain.applicant.Applicant;
import kr.mashup.branding.domain.application.form.ApplicationForm;
import kr.mashup.branding.domain.application.form.Question;
import kr.mashup.branding.domain.application.form.QuestionRequestVo;
import kr.mashup.branding.domain.application.form.QuestionType;
import kr.mashup.branding.domain.team.CreateTeamVo;
import kr.mashup.branding.domain.team.Team;

class ApplicationTest {
    @DisplayName("개인정보처리방침 동의여부가 false 이면 제출시 예외발생")
    @Test
    void submit_throwException_whenPrivacyPolicyIsNotAgreed() throws Exception {
        // given
        Boolean privacyPolicyAgreed = false;
        ApplicationForm applicationForm = createApplicationForm();
        Application sut = Application.of(createApplicant(), applicationForm);
        ReflectionTestUtils.setField(sut, "privacyPolicyAgreed", false);
        ReflectionTestUtils.setField(sut.getAnswers().get(0), "answerId", 1L);
        ReflectionTestUtils.setField(sut.getAnswers().get(1), "answerId", 2L);
        ReflectionTestUtils.setField(sut.getAnswers().get(2), "answerId", 3L);
        AtomicLong atomicLong = new AtomicLong();
        ApplicationSubmitRequestVo applicationSubmitRequestVo = ApplicationSubmitRequestVo.of(
            "applicantName",
            "010-1234-1234",
            LocalDate.now(),
            "매쉬업",
            "제주도",
            applicationForm.getQuestions()
                .stream()
                .map(it -> AnswerRequestVo.of(atomicLong.incrementAndGet(), it.getQuestionId(), "answerContent"))
                .collect(Collectors.toList()),
            privacyPolicyAgreed
        );
        // when
        Executable executable = () -> sut.submit(applicationSubmitRequestVo);
        // then
        Assertions.assertThrows(PrivacyPolicyNotAgreedException.class, executable);
    }

    @DisplayName("개인정보처리방침 동의여부가 null 이면 제출시 예외발생")
    @Test
    void submit_throwException_whenPrivacyPolicyIsNull() throws Exception {
        // given
        Boolean privacyPolicyAgreed = null;
        ApplicationForm applicationForm = createApplicationForm();
        Application sut = Application.of(createApplicant(), applicationForm);
        ReflectionTestUtils.setField(sut, "privacyPolicyAgreed", false);
        ReflectionTestUtils.setField(sut.getAnswers().get(0), "answerId", 1L);
        ReflectionTestUtils.setField(sut.getAnswers().get(1), "answerId", 2L);
        ReflectionTestUtils.setField(sut.getAnswers().get(2), "answerId", 3L);
        AtomicLong atomicLong = new AtomicLong();
        ApplicationSubmitRequestVo applicationSubmitRequestVo = ApplicationSubmitRequestVo.of(
            "applicantName",
            "010-1234-1234",
            LocalDate.now(),
            "매쉬업",
            "제주도",
            applicationForm.getQuestions()
                .stream()
                .map(it -> AnswerRequestVo.of(atomicLong.incrementAndGet(), it.getQuestionId(), "answerContent"))
                .collect(Collectors.toList()),
            privacyPolicyAgreed
        );
        // when
        Executable executable = () -> sut.submit(applicationSubmitRequestVo);
        // then
        Assertions.assertThrows(PrivacyPolicyNotAgreedException.class, executable);
    }

    private ApplicationForm createApplicationForm() {
        Team team = Team.of(CreateTeamVo.of("teamName"));
        ApplicationForm applicationForm = ApplicationForm.of(team, "applicationFormName");
        List<Question> questions = Arrays.asList(
            Question.of(applicationForm, QuestionRequestVo.of(
                "이름을 입력해 주세요",
                null,
                "ex) 홍길동",
                true,
                QuestionType.SINGLE_LINE_TEXT
            )),
            Question.of(applicationForm, QuestionRequestVo.of(
                "나이를 입력해 주세요",
                null,
                "ex) 25세",
                true,
                QuestionType.SINGLE_LINE_TEXT
            )),
            Question.of(applicationForm, QuestionRequestVo.of(
                "매쉬업에서 이루고자 하는 목표가 있다면 말씀해주세요",
                500,
                "ex) 앱스토어 1등하는 앱 배포하기",
                false,
                QuestionType.SINGLE_LINE_TEXT
            ))
        );
        applicationForm.addQuestions(questions);
        ReflectionTestUtils.setField(questions.get(0), "questionId", 1L);
        ReflectionTestUtils.setField(questions.get(1), "questionId", 2L);
        ReflectionTestUtils.setField(questions.get(2), "questionId", 3L);
        return applicationForm;
    }

    private Applicant createApplicant() throws Exception {
        Constructor<Applicant> declaredConstructor = Applicant.class.getDeclaredConstructor();
        declaredConstructor.setAccessible(true);
        return declaredConstructor.newInstance();
    }
}