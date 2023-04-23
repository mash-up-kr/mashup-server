package kr.mashup.branding.domain.storage;

import kr.mashup.branding.domain.BaseEntity;
import kr.mashup.branding.util.JsonConverter;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import java.util.Map;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Storage extends BaseEntity {
    @Column(unique = true)
    private String keyString;

    @Setter
    @Convert(converter = JsonConverter.class)
    private Map<String, Object> valueMap;

    public static Storage of(
        String keyString,
        Map<String, Object> valueMap
    ) {
        return new Storage(keyString, valueMap);
    }

    private Storage(String keyString, Map<String, Object> valueMap) {
        this.keyString = keyString;
        this.valueMap = valueMap;
    }
}
