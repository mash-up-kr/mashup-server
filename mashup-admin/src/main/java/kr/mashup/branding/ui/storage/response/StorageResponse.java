package kr.mashup.branding.ui.storage.response;

import kr.mashup.branding.domain.storage.Storage;
import lombok.Getter;
import lombok.Value;

import java.util.Map;

@Getter
@Value(staticConstructor = "of")
public class StorageResponse {
    String keyString;
    Map<String, Object> valueMap;

    public static StorageResponse from(Storage storage) {
        return StorageResponse.of(storage.getKeyString(), storage.getValueMap());
    }
}
