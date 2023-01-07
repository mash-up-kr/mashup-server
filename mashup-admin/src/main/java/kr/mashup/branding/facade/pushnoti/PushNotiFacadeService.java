package kr.mashup.branding.facade.pushnoti;

import kr.mashup.branding.domain.pushnoti.vo.PushNotiSendVo;
import kr.mashup.branding.infrastructure.pushnoti.PushNotiEventPublisher;
import kr.mashup.branding.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PushNotiFacadeService {
    private final MemberService memberService;
    private final PushNotiEventPublisher pushNotiEventPublisher;

    public void sendPushNotiToAllMembers(String title, String body) {
        pushNotiEventPublisher.publishPushNotiSendEvent(
            new PushNotiSendVo(
                memberService.getAllPushNotiTargetableFcmTokens(),
                title,
                body
            )
        );
    }
}
