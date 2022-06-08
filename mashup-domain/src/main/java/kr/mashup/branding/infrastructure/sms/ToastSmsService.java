package kr.mashup.branding.infrastructure.sms;

import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

import kr.mashup.branding.service.notification.sms.SmsWhitelistServiceImpl;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import kr.mashup.branding.domain.notification.NotificationStatus;
import kr.mashup.branding.domain.notification.sms.SmsNotificationStatus;
import kr.mashup.branding.domain.notification.sms.SmsRequestVo;
import kr.mashup.branding.domain.notification.sms.SmsSendFailedException;
import kr.mashup.branding.domain.notification.sms.SmsSendResultRecipientVo;
import kr.mashup.branding.domain.notification.sms.SmsSendResultVo;
import kr.mashup.branding.service.notification.sms.SmsService;
import kr.mashup.branding.service.notification.sms.SmsWhitelistService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 지원하는 문자 길이는 아래와 같습니다.
 * 최대 지원 글자 수는 저장 기준이며 문자 잘림을 방지하기 위해서 표준 규격으로 작성해주세요.
 * 인코딩은 EUC-KR 기준으로 발송되며 지원하지 않는 이모티콘은 발송에 실패합니다.
 *
 * 분류	최대 지원	표준 규격
 * SMS 본문	255자	90바이트(한글 45자, 영문 90자)
 * MMS 제목	120자	40바이트(한글 20자, 영문 40자)
 * MMS 본문	4,000자	2,000바이트(한글 1,000자, 영문 2,000자)
 *
 *
 * @see <a href="https://docs.toast.com/ko/Notification/SMS/ko/api-guide/">https://docs.toast.com/ko/Notification/SMS/ko/api-guide/</a>
 */
@Slf4j
@RequiredArgsConstructor
public class ToastSmsService implements SmsService {
    private static final Charset CHARSET_EUC_KR = Charset.forName("euc-kr");
    private static final int SMS_MAX_LENGTH = 90;

    private final String toastUrl;
    private final String appKey;
    private final RestTemplate toastRestTemplate;
    private final ObjectProvider<SmsWhitelistService> smsWhitelistService;

    @Override
    public SmsSendResultVo send(SmsRequestVo smsRequestVo) {
        ToastSmsRequest toastSmsRequest = toToastSmsRequest(smsRequestVo);
        HttpEntity<ToastSmsRequest> httpEntity = new HttpEntity<>(
            toastSmsRequest,
            new HttpHeaders()
        );

        final ResponseEntity<ToastSmsResponse> responseEntity;
        try {
            responseEntity = toastRestTemplate.exchange(
                toastUrl + "/sms/v3.0/appKeys/" + appKey + "/sender/" + resolveRequestType(smsRequestVo.getContent()),
                HttpMethod.POST,
                httpEntity,
                ToastSmsResponse.class
            );
        } catch (RestClientException e) {
            // request timeout 등 요청 만들다가 에러
            // read timeout 요청 보냈는데 답을 못받아서 에러
            // 응답 받았으나 status code 가 성공이 아님
            log.error("Failed while sending request to Toast SMS Api. toastSmsRequest: {}", toastSmsRequest, e);
            throw new SmsSendFailedException();
        }
        // 응답 잘 받았고, 내용이 성공
        // 응답 잘 받았고, 내용이 실패
        ToastSmsResponse toastSmsResponse = responseEntity.getBody();
        if (toastSmsResponse == null || !toastSmsResponse.isSuccess()) {
            log.error(
                "Failed to send SMS. toastSmsResponse: " + toastSmsResponse + ", toastSmsRequest: " + toastSmsRequest);
        }
        return toSmsResultVo(toastSmsResponse);
    }

    /**
     * 글자수에 따라 SMS, MMS 를 구분한다.
     * @return sms or mms
     */
    private String resolveRequestType(String content) {
        return content.getBytes(CHARSET_EUC_KR).length > SMS_MAX_LENGTH ? "mms" : "sms";
    }

    private ToastSmsRequest toToastSmsRequest(SmsRequestVo smsRequestVo) {
        return ToastSmsRequest.of(
            smsRequestVo.getContent(),
            Optional.ofNullable(smsRequestVo.getSenderPhoneNumber())
                .map(it -> it.replaceAll("-", ""))
                .orElse(null),
            smsRequestVo.getMessageId(),
            smsRequestVo.getSmsRecipientRequestVos()
                .stream()
                .map(it -> ToastSmsRecipient.of(
                    Optional.ofNullable(it.getPhoneNumber())
//                        .filter(this::isInWhitelist)
                        .map(phoneNumber -> phoneNumber.replaceAll("-", ""))
                        .orElse(null),
                    it.getMessageId()
                ))
                .collect(Collectors.toList())
        );
    }

    /**
     * 문자 발송을 시도해도 되는 전화번호인지 검사
     * @see SmsWhitelistServiceImpl
     * @param phoneNumber 수신자 전화번호
     * @return 문자 발송을 시도해도되는지 여부
     */
    private boolean isInWhitelist(String phoneNumber) {
        SmsWhitelistService service = smsWhitelistService.getIfAvailable();
        // 운영환경에서는 smsWhitelistService 가 존재하지 않고, 검사할 필요도 없다.
        if (service == null) {
            return true;
        }
        return service.contains(phoneNumber);
    }

    private SmsSendResultVo toSmsResultVo(ToastSmsResponse toastSmsResponse) {
        Assert.notNull(toastSmsResponse, "'toastSmsResponse' must not be null");
        if (toastSmsResponse.getBody() == null || toastSmsResponse.getBody().getData() == null) {
            log.error("Failed to parse toastSmsResponse. toastSmsResponse: {}", toastSmsResponse);
            return SmsSendResultVo.of(
                null,
                null,
                NotificationStatus.FAILURE,
                Collections.emptyList(),
                String.valueOf(toastSmsResponse.getHeader().getResultCode()),
                toastSmsResponse.getHeader().getResultMessage()
            );
        }
        return SmsSendResultVo.of(
            toastSmsResponse.getBody().getData().getRequestId(),
            toastSmsResponse.getBody().getData().getSenderGroupingKey(),
            toNotificationStatus(toastSmsResponse),
            toastSmsResponse.getBody().getData().getSendResultList()
                .stream()
                .map(this::toSmsSendResultRecipientVo)
                .collect(Collectors.toList()),
            String.valueOf(toastSmsResponse.getHeader().getResultCode()),
            toastSmsResponse.getHeader().getResultMessage()
        );
    }

    private NotificationStatus toNotificationStatus(ToastSmsResponse toastSmsResponse) {
        boolean isSuccess = toastSmsResponse.getHeader().getIsSuccessful();
        if (!isSuccess) {
            return NotificationStatus.FAILURE;
        }
        Integer toastSmsStatusCode = Optional.ofNullable(toastSmsResponse)
            .map(ToastSmsResponse::getBody)
            .map(ToastSmsResponse.ToastSmsResponseBody::getData)
            .map(ToastSmsResponse.ToastSmsResponseBodyData::getStatusCode)
            .orElse(null);
        if (toastSmsStatusCode == null) {
            return NotificationStatus.UNKNOWN;
        }
        switch (toastSmsStatusCode) {
            case 1:
                return NotificationStatus.IN_PROGRESS;
            case 2:
                return NotificationStatus.SUCCESS;
            case 3:
                return NotificationStatus.FAILURE;
            default:
                log.error("Unknown toastSmsStatusCode. toastSmsStatusCode: {}", toastSmsResponse);
                return NotificationStatus.UNKNOWN;
        }
    }

    private SmsSendResultRecipientVo toSmsSendResultRecipientVo(ToastSmsResponse.ToastSendResult toastSendResult) {
        Assert.notNull(toastSendResult, "'toastSendResult' must not be null");
        return SmsSendResultRecipientVo.of(
            String.valueOf(toastSendResult.getRecipientSeq()),
            toastSendResult.getRecipientGroupingKey(),
            toastSendResult.getResultCode() != null && toastSendResult.getResultCode() == 0
                ? SmsNotificationStatus.SUCCESS
                : SmsNotificationStatus.FAILURE,
            String.valueOf(toastSendResult.getResultCode()),
            toastSendResult.getResultMessage()
        );
    }
}
