package kr.mashup.branding.util;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.NoArgsConstructor;

@JsonSerialize
@NoArgsConstructor(staticName = "of")
public class EmptyResponse {
}
