package kr.mashup.branding.service.pushnoti;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class AttendancePushNotiReservationService implements PushNotiReservationService<Long> {
    private final ConcurrentHashMap<Long, Timer> reservationMap = new ConcurrentHashMap<>();

    @Override
    public void reserve(Long key, String message, LocalDateTime reserveAt) {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                sendPushNotification(message);
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

    private void sendPushNotification(String message) {
        //TODO
        System.out.println(message);
    }
}
