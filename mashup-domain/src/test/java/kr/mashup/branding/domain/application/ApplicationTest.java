package kr.mashup.branding.domain.application;

import java.lang.reflect.Constructor;
import java.util.Collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.test.util.ReflectionTestUtils;

import kr.mashup.branding.domain.applicant.Applicant;
import kr.mashup.branding.domain.application.form.ApplicationForm;
import kr.mashup.branding.domain.team.CreateTeamVo;
import kr.mashup.branding.domain.team.Team;

class ApplicationTest {
    @DisplayName("개인정보처리방침 동의여부가 false 이면 임시저장시 예외발생")
    @Test
    void update_throwException_whenPrivacyPolicyIsNotAgreed() throws Exception {
        // given

        Application sut = Application.of(createApplicant(), createApplicationForm());
        // when, then
        Assertions.assertThrows(
            PrivacyPolicyNotAgreedException.class,
            () -> sut.update(UpdateApplicationVo.of(
                "applicantName",
                "phoneNumber",
                Collections.emptyList(),
                false
            ))
        );
    }

    @DisplayName("개인정보처리방침 동의여부가 null 이면 임시저장시 예외발생")
    @Test
    void update_throwException_whenPrivacyPolicyIsNull() throws Exception {
        // given
        Application sut = Application.of(createApplicant(), createApplicationForm());
        // when
        Executable executable = () -> sut.update(UpdateApplicationVo.of(
            "applicantName",
            "phoneNumber",
            Collections.emptyList(),
            null
        ));
        // then
        Assertions.assertThrows(PrivacyPolicyNotAgreedException.class, executable);
    }

    @DisplayName("개인정보처리방침 동의여부가 false 이면 제출시 예외발생")
    @Test
    void submit_throwException_whenPrivacyPolicyIsNotAgreed() throws Exception {
        // given
        Application sut = Application.of(createApplicant(), createApplicationForm());
        ReflectionTestUtils.setField(sut, "privacyPolicyAgreed", false);
        // when
        Executable executable = sut::submit;
        // then
        Assertions.assertThrows(PrivacyPolicyNotAgreedException.class, executable);
    }

    @DisplayName("개인정보처리방침 동의여부가 null 이면 제출시 예외발생")
    @Test
    void submit_throwException_whenPrivacyPolicyIsNull() throws Exception {
        // given
        Application sut = Application.of(createApplicant(), createApplicationForm());
        ReflectionTestUtils.setField(sut, "privacyPolicyAgreed", null);
        // when
        Executable executable = sut::submit;
        // then
        Assertions.assertThrows(PrivacyPolicyNotAgreedException.class, executable);
    }

    private ApplicationForm createApplicationForm() {
        Team team = Team.of(CreateTeamVo.of("teamName"));
        return ApplicationForm.of(team, Collections.emptyList(), "applicationFormName");
    }

    private Applicant createApplicant() throws Exception {
        Constructor<Applicant> declaredConstructor = Applicant.class.getDeclaredConstructor();
        declaredConstructor.setAccessible(true);
        return declaredConstructor.newInstance();
    }
}