package kr.mashup.branding.domain.application;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.mashup.branding.domain.applicant.Applicant;
import kr.mashup.branding.domain.application.form.ApplicationForm;

interface ApplicationRepository extends JpaRepository<Application, Long>, ApplicationRepositoryCustom {
    List<Application> findByApplicant_applicantIdAndStatusIn(Long applicantId, Collection<ApplicationStatus> statuses);

    List<Application> findByApplicantAndApplicationForm(Applicant applicant, ApplicationForm applicationForm);

    Optional<Application> findByApplicationIdAndApplicant_applicantId(Long applicationId, Long applicantId);

    List<Application> findByApplicationForm_ApplicationFormId(Long applicationFormId);
}
