package kr.mashup.branding.ui.content.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ContentResponse {

    private Long contentId;

    private String content;
}
