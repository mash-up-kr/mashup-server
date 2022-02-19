package kr.mashup.branding.domain.adminmember;

import lombok.Getter;

@Getter
public enum Position {
    MASHUP_LEADER(Team.values()),
    MASHUP_SUBLEADER(Team.values()),
    BRANDING_LEADER(Team.values()),
    BRANDING_SUBLEADER(Team.values()),
    BRANDING_MEMBER(Team.values()),
    SPRING_LEADER(Team.SPRING),
    SPRING_SUBLEADER(Team.SPRING),
    NODE_LEADER(Team.NODE),
    NODE_SUBLEADER(Team.NODE),
    iOS_LEADER(Team.iOS),
    iOS_SUBLEADER(Team.iOS),
    ANDROID_LEADER(Team.ANDROID),
    ANDROID_SUBLEADER(Team.ANDROID),
    WEB_LEADER(Team.WEB),
    WEB_SUBLEADER(Team.WEB),
    DESIGN_LEADER(Team.DESIGN),
    DESIGN_SUBLEADER(Team.DESIGN);

    private final Team[] authorities;

    Position(Team... authorities) {
        this.authorities = authorities;
    }

    public enum Team {
        SPRING, NODE, iOS, ANDROID, WEB, DESIGN,
    }
}
