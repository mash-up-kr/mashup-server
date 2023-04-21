package kr.mashup.branding.ui.storage.request;

import lombok.Getter;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.util.Map;

@Getter
@Value(staticConstructor = "of")
public class StorageRequest {
    @NotNull
    String keyString;

    @NotNull
    Map<String, String> valueMap;
}
