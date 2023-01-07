package kr.mashup.branding.repository.emailnotification;

import kr.mashup.branding.domain.email.EmailRequest;

import java.util.List;

public interface EmailRequestRepositoryCustom {

    List<EmailRequest> findByApplicationId(Long applicationId);
}
