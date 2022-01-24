package kr.mashup.branding.domain.sms;

import kr.mashup.branding.domain.sms.dto.ToastSmsRequest;
import kr.mashup.branding.domain.sms.dto.ToastSmsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
public class ToastSmsServiceImpl implements ToastSmsService {

    @Value("${toast.sms.url}")
    private String toastUrl;

    @Value("${toast.sms.app-key}")
    private String appKey;

    @Value("${toast.sms.secret-key}")
    private String secretKey;

    private final RestTemplate toastRestTemplate;

    @Override
    public ToastSmsResponse send(ToastSmsRequest toastSmsRequest) {
        HttpHeaders headers = buildHeader();
        HttpEntity httpEntity = new HttpEntity(toastSmsRequest, headers);
        ResponseEntity<ToastSmsResponse> exchange = toastRestTemplate.exchange(
                toastUrl + "/sms/v3.0/appKeys/" + appKey + "/sender/sms",
                HttpMethod.POST,
                httpEntity,
                ToastSmsResponse.class
        );

        return exchange.getBody();
    }

    private HttpHeaders buildHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Secret-Key", secretKey);

        return headers;
    }

}
