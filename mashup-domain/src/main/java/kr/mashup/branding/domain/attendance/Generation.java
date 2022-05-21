package kr.mashup.branding.domain.attendance;

import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
public class Generation extends BaseEntity{

    private Integer number;

    private LocalDate statedAt;

    private LocalDate ended_at;
}
