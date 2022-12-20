package kr.mashup.branding.infrastructure.email;


import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.SendTemplatedEmailRequest;
import com.amazonaws.services.simpleemail.model.SendTemplatedEmailResult;
import com.google.gson.Gson;
import kr.mashup.branding.domain.email.EmailMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SesEmailService {

    private final AmazonSimpleEmailService amazonSimpleEmailService;

    public SesResponseStatus sendEmail(EmailMetadata metadata){

        List<String> receivers = new ArrayList<>();
        receivers.add(metadata.getDest());

        Destination des = new Destination();
        des.setToAddresses(receivers);

        String templateData = new Gson().toJson(metadata.getBindingData());

        SendTemplatedEmailRequest emailRequest = new SendTemplatedEmailRequest();
        emailRequest.setTemplate(metadata.getTemplateName());
        emailRequest.setDestination(des);
        emailRequest.setSource(metadata.getSrc());
        emailRequest.setTemplateData(templateData);

        SendTemplatedEmailResult result = amazonSimpleEmailService.sendTemplatedEmail(emailRequest);

        if (result.getSdkHttpMetadata().getHttpStatusCode() != 200){
            return SesResponseStatus.FAIL;
        }

        return SesResponseStatus.SUCCESS;
    }
}
