package kr.mashup.branding.domain.team;

import kr.mashup.branding.domain.generation.Generation;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Value(staticConstructor = "of")
public class CreateTeamVo {

    @NotNull Generation generation;

    @NotBlank(message = "'name' must not be blank")
    String name;
}
