package kr.mashup.branding.service.pushnoti;

import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.*;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class AttendancePushNotiReservationService implements PushNotiReservationService<Long> {
    private final FirebaseApp firebaseApp;
    private final ConcurrentHashMap<Long, Timer> reservationMap = new ConcurrentHashMap<>();

    @Override
    public void reserve(Long key, List<String> tokens, String title, String message, LocalDateTime reserveAt) {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @SneakyThrows
            @Override
            public void run() {
                sendPushNotification(tokens, title, message);
            }
        };
        timer.schedule(timerTask, Date.from(reserveAt.toInstant(ZoneOffset.ofHours(9))));
        reservationMap.put(key, timer);
    }

    @Override
    public void cancel(Long key) {
        Timer timer = reservationMap.remove(key);
        timer.cancel();
    }

    @Override
    public void sendPushNotification(List<String> tokens, String title, String message) throws FirebaseMessagingException {
        log.info("send push notification: " + message);
        MulticastMessage multicastMessage = MulticastMessage.builder()
                .putData("title", title)
                .putData("body", message)
                .addAllTokens(tokens)
                .build();
        FirebaseMessaging.getInstance(firebaseApp).sendMulticast(multicastMessage);
    }
}
