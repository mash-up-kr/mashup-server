package kr.mashup.branding.ui.rnb.response;

import lombok.Getter;
import lombok.Value;

import java.util.List;

@Getter
@Value(staticConstructor = "of")
public class RnbMetaResponse {

    List<String> menus;
}
