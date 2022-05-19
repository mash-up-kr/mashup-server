package kr.mashup.branding.domain.notification.sms.whitelist;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * 개발환경에서 실제 문자 발송을 제한하기위한 테이블
 * 아래 테이블에 등록된 번호만 실제 발송요청을 보내고, 등록되지 않은 번호는 발송요청을 보내지 않는다.
 */
@Entity
@Getter
@ToString(of = {"smsWhiteListId", "phoneNumber"})
@EqualsAndHashCode(of = {"smsWhiteListId"})
@EntityListeners(AuditingEntityListener.class)
public class SmsWhitelist {
    @Id
    @GeneratedValue
    private Long smsWhiteListId;

    @Column(unique = true)
    private String phoneNumber;

    @CreatedDate
    private LocalDateTime createdAt;

    @CreatedBy
    private String createdBy;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @LastModifiedBy
    private String updatedBy;

    public static SmsWhitelist of(String phoneNumber) {
        SmsWhitelist smsWhitelist = new SmsWhitelist();
        smsWhitelist.phoneNumber = phoneNumber;
        return smsWhitelist;
    }
}
