package kr.mashup.branding.repository.application;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import kr.mashup.branding.domain.applicant.Applicant;
import kr.mashup.branding.domain.application.Application;
import kr.mashup.branding.domain.application.ApplicationQueryVo;
import kr.mashup.branding.domain.application.ApplicationStatus;
import kr.mashup.branding.domain.application.form.ApplicationForm;
import kr.mashup.branding.domain.generation.Generation;
import org.springframework.data.domain.Page;

public interface ApplicationRepositoryCustom {

    Page<Application> findBy(Generation generation, ApplicationQueryVo applicationQueryVo);

    boolean existByApplicationForm(Long applicationFormId);

    boolean existByGenerationAndApplicantAndApplicationStatus(Generation generation, Long applicant, ApplicationStatus status);

    List<Application> findByIdAndStatusIn(Long applicantId, Collection<ApplicationStatus> statuses);

    List<Application> findByApplicantAndApplicationForm(Applicant applicant, ApplicationForm applicationForm);

    Optional<Application> findByApplicationAndApplicant(Long applicationId, Long applicantId);

    List<Application> findByApplicationForm(Long applicationFormId);

    List<Application> findByStatusAndCreatedAtBefore(ApplicationStatus status, LocalDateTime eventOccurredAt);

    List<Application> findApplicationsByApplicantIn(Generation generation, List<Applicant> applicants);

    List<Application> findInterviewerByTeamId(Long teamId);

}
/**
 * Applicant 연관관계
 * many to one: applicant, applicationForm
 * one to one : applicationResult, confirmation
 */
