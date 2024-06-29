package kr.mashup.branding.domain.randommessage;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "danggn_today_message")
public class RandomMessage {
    @Id
    @GeneratedValue
    private Long id;

    private String message;

    @Enumerated(EnumType.STRING)
    private RandomMessageType type;

    private RandomMessage(String message, RandomMessageType type) {
        this.message = message;
        this.type = type;
    }

    public static RandomMessage of(String message, RandomMessageType type) {
        return new RandomMessage(message, type);
    }

    public void updateMessage(String message) {
        this.message = message;
    }
}
