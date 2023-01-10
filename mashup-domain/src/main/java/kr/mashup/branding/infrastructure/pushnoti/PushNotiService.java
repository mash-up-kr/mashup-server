package kr.mashup.branding.infrastructure.pushnoti;

import kr.mashup.branding.domain.pushnoti.vo.PushNotiSendVo;

public interface PushNotiService {
    void sendPushNotification(PushNotiSendVo pushNotiSendVo);
}
