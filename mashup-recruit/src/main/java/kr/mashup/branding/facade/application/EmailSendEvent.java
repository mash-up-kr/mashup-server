package kr.mashup.branding.facade.application;

import kr.mashup.branding.domain.email.EmailTemplateName;
import lombok.Value;

import java.util.Map;

@Value(staticConstructor = "of")
public class EmailSendEvent {
    String senderEmail;
    String receiverEmail;
    EmailTemplateName emailTemplateName;
    Map<String, String> bindingData;
}
