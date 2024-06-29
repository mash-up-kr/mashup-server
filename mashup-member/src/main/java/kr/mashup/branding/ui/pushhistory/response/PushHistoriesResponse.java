package kr.mashup.branding.ui.pushhistory.response;

import kr.mashup.branding.domain.pushnoti.LinkType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Value(staticConstructor = "of")
public class PushHistoriesResponse {

    List<PushHistoryResponse> read;
    List<PushHistoryResponse> unread;

    @Getter
    @Value(staticConstructor = "of")
    public static class PushHistoryResponse{
        String pushType;
        String title;
        String body;
        LinkType linkType;
        LocalDateTime sendTime;
    }
}
