package kr.mashup.branding.repository.application;

import kr.mashup.branding.domain.applicant.Applicant;
import kr.mashup.branding.domain.application.Application;
import kr.mashup.branding.domain.application.ApplicationStatus;
import kr.mashup.branding.domain.application.form.ApplicationForm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Long>, ApplicationRepositoryCustom {
    List<Application> findByApplicant_applicantIdAndStatusIn(Long applicantId, Collection<ApplicationStatus> statuses);

    List<Application> findByApplicantAndApplicationForm(Applicant applicant, ApplicationForm applicationForm);

    Optional<Application> findByApplicationIdAndApplicant_applicantId(Long applicationId, Long applicantId);

    List<Application> findByApplicationForm_ApplicationFormId(Long applicationFormId);

    List<Application> findByStatusAndCreatedAtBefore(ApplicationStatus status, LocalDateTime eventOccurredAt);

    boolean existsByApplicationForm_ApplicationFormId(Long applicationFormId);

    boolean existsByApplicant_applicantIdAndStatus(Long applicant, ApplicationStatus status);

    List<Application> findApplicationsByApplicantIn(List<Applicant> applicants);
}
/**
 * Applicant 연관관계
 * many to one: applicant, applicationForm
 * one to one : applicationResult, confirmation
 */
