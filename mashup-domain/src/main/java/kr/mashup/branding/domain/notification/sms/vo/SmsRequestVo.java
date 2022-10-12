package kr.mashup.branding.domain.notification.sms.vo;

import java.util.List;
import java.util.stream.Collectors;

import kr.mashup.branding.domain.adminmember.entity.AdminMember;
import kr.mashup.branding.domain.notification.Notification;
import lombok.Value;

@Value(staticConstructor = "of")
public class SmsRequestVo {
    String messageId;
    String senderPhoneNumber;
    String content;
    List<SmsRecipientRequestVo> smsRecipientRequestVos;

    public static SmsRequestVo from(final Notification notification){

        final AdminMember sender = notification.getSender();
        final List<SmsRecipientRequestVo> SmsRecipientRequestVos = notification
            .getSmsRequests()
            .stream()
            .map(it -> SmsRecipientRequestVo.of(
                it.getMessageId(),
                it.getRecipientPhoneNumber()
            ))
            .collect(Collectors.toList());

        return SmsRequestVo.of(
            notification.getMessageId(),
            sender.getPhoneNumber(),
            notification.getContent(),
            SmsRecipientRequestVos
        );
    }
}
