package kr.mashup.branding.domain.attendance;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

@Entity
public class AttendanceCode extends BaseEntity{

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "content_id")
    private Content content;

    private String code;

    private LocalDateTime startedAt;

    private LocalDateTime endedAt;

}
