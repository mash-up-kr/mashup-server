package kr.mashup.branding.domain.application;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.mashup.branding.domain.application.form.ApplicationForm;

interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByStatusIn(Collection<ApplicationStatus> statuses);

    // TODO: applicant
    List<Application> findByApplicationForm(ApplicationForm applicationForm);
}
