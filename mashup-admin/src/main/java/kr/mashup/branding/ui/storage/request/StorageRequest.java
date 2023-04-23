package kr.mashup.branding.ui.storage.request;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.Map;

@Getter
public class StorageRequest {
    @NotNull
    String keyString;

    @NotNull
    Map<String, Object> valueMap;
}
