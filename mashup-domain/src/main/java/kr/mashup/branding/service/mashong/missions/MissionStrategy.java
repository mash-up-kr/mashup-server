package kr.mashup.branding.service.mashong.missions;

import kr.mashup.branding.domain.member.MemberGeneration;

interface MissionStrategy {
    boolean isAchieved(MemberGeneration memberGeneration);

    String getTitle();

    String getDescription();
}
