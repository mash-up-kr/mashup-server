package kr.mashup.branding.infrastructure.email;


import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.SendTemplatedEmailRequest;
import com.amazonaws.services.simpleemail.model.SendTemplatedEmailResult;
import com.google.gson.Gson;
import kr.mashup.branding.domain.email.EmailMetadata;
import kr.mashup.branding.service.email.EmailResponse;
import kr.mashup.branding.service.email.EmailResponseStatus;
import kr.mashup.branding.service.email.EmailSendService;
import kr.mashup.branding.service.email.EmailSendVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SesEmailSendService implements EmailSendService {

    private final AmazonSimpleEmailService amazonSimpleEmailService;

    public EmailResponse sendEmail(EmailSendVo emailSendVo){

        final List<String> receivers = new ArrayList<>();
        receivers.add(emailSendVo.getReceiverEmail());

        final Destination dest = new Destination();
        dest.setToAddresses(receivers);

        final String templateData = new Gson().toJson(emailSendVo.getBindingData());

        final SendTemplatedEmailRequest emailRequest = new SendTemplatedEmailRequest();
        emailRequest.setTemplate(emailSendVo.getTemplateName().getRegisteredTemplateName());
        emailRequest.setDestination(dest);
        emailRequest.setSource(emailSendVo.getSenderEmail());
        emailRequest.setTemplateData(templateData);

        final SendTemplatedEmailResult result = amazonSimpleEmailService.sendTemplatedEmail(emailRequest);

        if (result.getSdkHttpMetadata().getHttpStatusCode() != 200){
            return EmailResponse.fail();
        }

        return EmailResponse.success(result.getMessageId());
    }
}
