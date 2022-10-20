package kr.mashup.branding.service.team;

import kr.mashup.branding.domain.team.Team;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor(staticName = "of")
public class TeamVo {

    private final Long teamId;

    private final String name;

    private final String createdBy;

    private final LocalDateTime createdAt;

    private final String updatedBy;

    private final LocalDateTime updatedAt;

    public static TeamVo from(Team team){
        return TeamVo.of(
            team.getTeamId(),
            team.getName(),
            team.getCreatedBy(),
            team.getCreatedAt(),
            team.getUpdatedBy(),
            team.getUpdatedAt());
    }
}
