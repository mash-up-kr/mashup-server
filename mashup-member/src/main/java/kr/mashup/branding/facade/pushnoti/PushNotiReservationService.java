package kr.mashup.branding.facade.pushnoti;

import com.google.firebase.messaging.FirebaseMessagingException;

import java.time.LocalDateTime;
import java.util.List;

public interface PushNotiReservationService<K> {
    void reserve(K key, List<String> tokens,String title, String message, LocalDateTime reserveAt);

    void cancel(K key);

    void sendPushNotification(List<String> tokens, String title, String message) throws FirebaseMessagingException;
}
