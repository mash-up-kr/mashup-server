package kr.mashup.branding.domain.pushnoti.vo;

import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.pushnoti.DataKeyType;
import kr.mashup.branding.domain.pushnoti.LinkType;

import java.util.List;
import java.util.Map;

public class DanggnRewardUpdatedVo extends PushNotiSendVo {
    private static final String title = "%d주차 1등의 공지가 등록됐어요";
    private static final String body = "%s님의 공지 내용을 확인해보세요";
    private static final Map<String, String> dataMap = Map.of(DataKeyType.LINK.getKey(), LinkType.DANGGN_REWARD.toString());

    public DanggnRewardUpdatedVo(Integer number, Member member, List<Member> members) {
        super(members, String.format(title, number), String.format(body, member.getName()), dataMap);
    }
}
