package kr.mashup.branding.ui.member.request;

import kr.mashup.branding.domain.member.MemberStatus;
import kr.mashup.branding.domain.member.Platform;
import lombok.Getter;

import java.util.List;

@Getter
public class MemberStatusUpdateRequest {
    List<Long> memberIds;
    MemberStatus memberStatus;
    Platform platform;
}
