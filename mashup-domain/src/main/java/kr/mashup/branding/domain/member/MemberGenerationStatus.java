package kr.mashup.branding.domain.member;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MemberGenerationStatus {
    ACTIVE("정상 활동"),
    DROP_OUT("중도 하차");

    private final String description;
}
