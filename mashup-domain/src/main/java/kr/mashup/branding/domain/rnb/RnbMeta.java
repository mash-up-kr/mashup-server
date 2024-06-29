package kr.mashup.branding.domain.rnb;

import kr.mashup.branding.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RnbMeta extends BaseEntity {

    private String menuName;

    @Enumerated(EnumType.STRING)
    private RnbPolicy policy;

    private Integer menuOrder;
}
