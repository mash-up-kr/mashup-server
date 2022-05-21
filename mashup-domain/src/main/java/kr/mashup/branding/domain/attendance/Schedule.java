package kr.mashup.branding.domain.attendance;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
public class Schedule extends BaseEntity{

    private String name;

    private LocalDateTime startedAt;

    private LocalDateTime endedAt;

    private String createdBy;

    private String updatedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "generation_id")
    private Generation generation;
}
