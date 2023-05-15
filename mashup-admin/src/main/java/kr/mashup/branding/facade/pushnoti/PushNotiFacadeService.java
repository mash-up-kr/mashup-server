package kr.mashup.branding.facade.pushnoti;

import java.util.List;
import java.util.Map;

import kr.mashup.branding.service.adminmember.AdminMemberService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.mashup.branding.domain.pushnoti.DataKeyType;
import kr.mashup.branding.domain.pushnoti.LinkType;
import kr.mashup.branding.domain.pushnoti.vo.PushNotiSendVo;
import kr.mashup.branding.infrastructure.pushnoti.PushNotiEventPublisher;
import kr.mashup.branding.service.member.MemberService;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PushNotiFacadeService {
    private final AdminMemberService adminMemberService;
    private final MemberService memberService;
    private final PushNotiEventPublisher pushNotiEventPublisher;

    public void sendPushNotiToAllMembers(String title, String body) {
        pushNotiEventPublisher.publishPushNotiSendEvent(
            new PushNotiSendVo(
                memberService.getAllPushNotiTargetableMembers(),
                title,
                body,
                Map.of(DataKeyType.LINK.getKey(), LinkType.MAIN.toString())
            )
        );
    }

    public void sendPushNotiToPartialMembers(List<Long> memberIds, String title, String body) {
        pushNotiEventPublisher.publishPushNotiSendEvent(
            new PushNotiSendVo(
                memberService.getPushNotiTargetableMembers(memberIds),
                title,
                body,
                Map.of(DataKeyType.LINK.getKey(), LinkType.MAIN.toString())
            )
        );
    }
}
