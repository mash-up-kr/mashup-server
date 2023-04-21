package kr.mashup.branding.domain.storage;

import kr.mashup.branding.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Storage extends BaseEntity {
    @Column(unique = true)
    private String keyString;

    @Setter
    private String valueMap;

    public static Storage of(
        String keyString,
        String valueMap
    ) {
        return new Storage(keyString, valueMap);
    }

    private Storage(String keyString, String valueMap) {
        this.keyString = keyString;
        this.valueMap = valueMap;
    }
}
