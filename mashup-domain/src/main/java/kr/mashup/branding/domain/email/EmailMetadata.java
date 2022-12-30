package kr.mashup.branding.domain.email;

import lombok.Value;

import java.util.Map;

@Value(staticConstructor = "of")
public class EmailMetadata {
    Long requestId;
    String templateName;
    Map<String, String> bindingData;
    String senderEmail;
    String receiverEmail;
}
