package kr.mashup.branding.service.pushnoti;

import java.time.LocalDateTime;

public interface PushNotiReservationService<K> {
    void reserve(K key, String message, LocalDateTime reserveAt);

    void cancel(K key);
}
