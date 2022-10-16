package kr.mashup.branding.repository.application;

import kr.mashup.branding.domain.applicant.Applicant;
import kr.mashup.branding.domain.application.Application;
import kr.mashup.branding.domain.application.ApplicationQueryRequest;
import kr.mashup.branding.domain.application.ApplicationQueryVo;
import kr.mashup.branding.domain.application.ApplicationStatus;
import kr.mashup.branding.domain.application.form.ApplicationForm;
import kr.mashup.branding.domain.generation.Generation;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ApplicationRepositoryCustom {

    Page<Application> findBy(Generation generation, ApplicationQueryVo applicationQueryVo);

    boolean existByApplicationForm(Long applicationFormId);

    boolean existByApplicantAndApplicationStatus(Long applicant, ApplicationStatus status);

    List<Application> findByIdAndStatusIn(Long applicantId, Collection<ApplicationStatus> statuses);

    List<Application> findByApplicantAndApplicationForm(Applicant applicant, ApplicationForm applicationForm);

    Optional<Application> findByApplicationAndApplicant(Long applicationId, Long applicantId);

    List<Application> findByApplicationForm(Long applicationFormId);

    List<Application> findByStatusAndCreatedAtBefore(ApplicationStatus status, LocalDateTime eventOccurredAt);

    List<Application> findApplicationsByApplicantIn(List<Applicant> applicants);

}
/**
 * Applicant 연관관계
 * many to one: applicant, applicationForm
 * one to one : applicationResult, confirmation
 */
