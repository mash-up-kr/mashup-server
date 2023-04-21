package kr.mashup.branding.ui.storage.response;

import kr.mashup.branding.domain.storage.Storage;
import kr.mashup.branding.util.JsonUtil;
import lombok.Getter;
import lombok.Value;

import java.io.IOException;
import java.util.Map;

@Getter
@Value(staticConstructor = "of")
public class StorageResponse {
    String keyString;
    Map<String, Object> valueMap;

    public static StorageResponse from(Storage storage) throws IOException {
        return StorageResponse.of(
            storage.getKeyString(),
            JsonUtil.deserialize(storage.getValueMap())
        );
    }
}
