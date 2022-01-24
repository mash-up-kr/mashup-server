package kr.mashup.branding.domain.sms;

import kr.mashup.branding.domain.sms.dto.ToastSmsRequest;
import kr.mashup.branding.domain.sms.dto.ToastSmsResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ToastSmsServiceImplTest {

    @Autowired ToastSmsServiceImpl toastSmsService;

    @Test
    void send() {
        ToastSmsRequest toastSmsRequest = new ToastSmsRequest();
        toastSmsRequest.setSendNo("01097944578");
        toastSmsRequest.setBody("test 문자");
        toastSmsRequest.setSenderGroupingKey("1");
        ToastSmsRequest.Recipient recipient = new ToastSmsRequest.Recipient();
        recipient.setRecipientNo("01097944578");
        recipient.setRecipientGroupingKey("Spring");

        toastSmsRequest.setRecipientList(List.of(recipient));

        ToastSmsResponse send = toastSmsService.send(toastSmsRequest);
        System.out.println(send);
    }

}