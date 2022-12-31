package kr.mashup.branding.service.email;

import kr.mashup.branding.domain.email.EmailMetadata;
import kr.mashup.branding.domain.email.EmailTemplateName;
import lombok.Value;

import java.util.Map;

@Value(staticConstructor = "of")
public class EmailSendVo {
    EmailTemplateName templateName;
    Map<String, String> bindingData;
    String senderEmail;
    String receiverEmail;

    public static EmailSendVo from(EmailMetadata metadata){
        return EmailSendVo.of(
            metadata.getTemplateName(),
            metadata.getBindingData(),
            metadata.getSenderEmail(),
            metadata.getReceiverEmail());
    }
}
