package kr.mashup.branding.service.email;

import kr.mashup.branding.domain.email.EmailMetadata;

public interface EmailSendService {

    EmailResponse sendEmail(EmailMetadata metadata);
}
