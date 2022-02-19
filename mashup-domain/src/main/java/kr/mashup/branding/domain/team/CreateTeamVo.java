package kr.mashup.branding.domain.team;

import lombok.Value;

@Value(staticConstructor = "of")
public class CreateTeamVo {
    String name;
}
