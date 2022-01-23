package kr.mashup.branding.domain.sms;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Entity
public class SmsRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long groupId;

    private Long userId;
    private String username;
    private String phoneNumber;

    private String content;

    private boolean isSuccess;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    private SmsRequest(Long groupId, Long userId, String username, String content, String phoneNumber) {
        this.groupId = groupId;
        this.userId = userId;
        this.username = username;
        this.content = content;
        this.phoneNumber = phoneNumber;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }
}
