package kr.mashup.branding.domain.sms;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import kr.mashup.branding.domain.sms.dto.ToastSmsRequest;
import kr.mashup.branding.domain.sms.dto.ToastSmsResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ToastSmsServiceImpl implements ToastSmsService {

    @Value("${sms.toast.url}")
    private String toastUrl;

    @Value("${sms.toast.app-key}")
    private String appKey;

    @Value("${sms.toast.secret-key}")
    private String secretKey;

    private final RestTemplate toastRestTemplate;

    @Override
    public ToastSmsResponse send(SmsRequestGroup requestGroup, List<SmsRequest> requests) {
        HttpHeaders headers = buildHeader();
        ToastSmsRequest toastSmsRequest = buildBody(requestGroup, requests);
        HttpEntity<ToastSmsRequest> httpEntity = new HttpEntity<>(toastSmsRequest, headers);

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

    private ToastSmsRequest buildBody(SmsRequestGroup smsRequestGroup, List<SmsRequest> requests) {
        List<ToastSmsRequest.Recipient> recipients = requests.stream()
                .map(smsRequest -> ToastSmsRequest.Recipient.of(smsRequest.getPhoneNumber(), smsRequest.getSmsSendKey()))
                .collect(Collectors.toList());
        return ToastSmsRequest.of(
                smsRequestGroup.getContent(),
                "01097944578",
                smsRequestGroup.getSmsRequestGroupId().toString(),
                recipients
        );
    }

}
