package kr.mashup.branding.domain.member;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MemberStatus {
    ACTIVE("활동 중"),
    INACTIVE("활동 종료"),
    RUN("중도 탈퇴");

    private final String description;
}
