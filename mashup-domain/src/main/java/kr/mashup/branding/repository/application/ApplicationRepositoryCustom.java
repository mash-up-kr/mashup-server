package kr.mashup.branding.repository.application;

import kr.mashup.branding.domain.application.Application;
import kr.mashup.branding.domain.application.ApplicationQueryVo;
import kr.mashup.branding.domain.application.ApplicationStatus;
import org.springframework.data.domain.Page;

public interface ApplicationRepositoryCustom {
    Page<Application> findBy(ApplicationQueryVo applicationQueryVo);
    boolean existByApplicationForm(Long applicationFormId);
    boolean existByApplicantAndApplicationStatus(Long applicant, ApplicationStatus status);

}
/**
 * Applicant 연관관계
 * many to one: applicant, applicationForm
 * one to one : applicationResult, confirmation
 */
