package kr.mashup.branding.domain.danggn;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DanggnTodayMessage {
    @Id
    @GeneratedValue
    private Long id;

    private String message;
}
