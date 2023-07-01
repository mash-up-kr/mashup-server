package kr.mashup.branding.ui.storage.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class KeyResponse {
    List<String> keyStrings;

    public static KeyResponse from(List<String> keyStrings) {
        return KeyResponse.builder()
                .keyStrings(keyStrings)
                .build();
    }
}
