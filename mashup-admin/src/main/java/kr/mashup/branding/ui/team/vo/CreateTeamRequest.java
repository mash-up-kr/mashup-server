package kr.mashup.branding.ui.team.vo;

import kr.mashup.branding.domain.team.CreateTeamVo;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CreateTeamRequest {
    private String name;

    public CreateTeamVo toCreateTeamVo(){
        return CreateTeamVo.of(name);
    }
}
