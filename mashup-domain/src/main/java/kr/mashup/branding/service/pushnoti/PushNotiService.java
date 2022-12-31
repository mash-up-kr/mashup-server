package kr.mashup.branding.service.pushnoti;

import com.google.firebase.messaging.FirebaseMessagingException;

import java.util.List;

public interface PushNotiService {
    void sendPushNotification(List<String> tokens, String title, String message);
}
