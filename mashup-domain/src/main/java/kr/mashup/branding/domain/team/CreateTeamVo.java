package kr.mashup.branding.domain.team;

import lombok.Value;

import javax.validation.constraints.NotBlank;

@Value(staticConstructor = "of")
public class CreateTeamVo {
    @NotBlank(message = "'name' must not be blank")
    String name;
}
