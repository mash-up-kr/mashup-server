package kr.mashup.branding.service.pushnoti;

import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.MulticastMessage;
import kr.mashup.branding.domain.pushnoti.exception.PushNotiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FcmPushNotiService implements PushNotiService{
    private final FirebaseApp firebaseApp;

    public void sendPushNotification(List<String> tokens, String title, String message) {
        log.info("send push notification: " + message);
        MulticastMessage multicastMessage = MulticastMessage.builder()
                .putData("title", title)
                .putData("body", message)
                .addAllTokens(tokens)
                .build();
        try {
            FirebaseMessaging.getInstance(firebaseApp).sendMulticast(multicastMessage);
        }
        catch (FirebaseMessagingException e){
            throw new PushNotiException();
        }
    }
}
