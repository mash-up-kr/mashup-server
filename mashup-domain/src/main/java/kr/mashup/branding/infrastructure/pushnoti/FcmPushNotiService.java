package kr.mashup.branding.infrastructure.pushnoti;

import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.MulticastMessage;
import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.pushnoti.exception.PushNotiException;
import kr.mashup.branding.domain.pushnoti.vo.PushNotiSendVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FcmPushNotiService implements PushNotiService {
    private final FirebaseApp firebaseApp;

    public void sendPushNotification(PushNotiSendVo pushNotiSendVo) {
        log.info("send push notification: " + pushNotiSendVo.getBody());
        MulticastMessage multicastMessage = MulticastMessage.builder()
            .putData("title", pushNotiSendVo.getTitle())
            .putData("body", pushNotiSendVo.getBody())
            .addAllTokens(getAgreedFcmTokens(pushNotiSendVo.getMembers()))
            .build();
        try {
            FirebaseMessaging.getInstance(firebaseApp).sendMulticast(multicastMessage);
        } catch (FirebaseMessagingException e) {
            throw new PushNotiException();
        }
    }

    private List<String> getAgreedFcmTokens(List<Member> members) {
        return members.stream()
            .filter(Member::getPushNotificationAgreed)
            .map(Member::getFcmToken)
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }
}
