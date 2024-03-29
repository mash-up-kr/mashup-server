package kr.mashup.branding.domain.adminmember.entity;

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
    SPRING_HELPER(Team.SPRING),

    NODE_LEADER(Team.NODE),
    NODE_SUBLEADER(Team.NODE),
    NODE_HELPER(Team.NODE),

    iOS_LEADER(Team.iOS),
    iOS_SUBLEADER(Team.iOS),
    iOS_HELPER(Team.iOS),

    ANDROID_LEADER(Team.ANDROID),
    ANDROID_SUBLEADER(Team.ANDROID),
    ANDROID_HELPER(Team.ANDROID),

    WEB_LEADER(Team.WEB),
    WEB_SUBLEADER(Team.WEB),
    WEB_HELPER(Team.WEB),

    DESIGN_LEADER(Team.DESIGN),
    DESIGN_SUBLEADER(Team.DESIGN),
    DESIGN_HELPER(Team.DESIGN);

    private final Team[] authorities;

    Position(Team... authorities) {
        this.authorities = authorities;
    }

    @Getter
    public enum Team {
        SPRING("Spring"), NODE("Node"), iOS("iOS"),
        ANDROID("Android"), WEB("Web"), DESIGN("Design");

        private final String name;

        Team(String name) {
            this.name = name;
        }
    }
}
