package kr.mashup.branding.dto.invite;

import kr.mashup.branding.dto.generation.GenerationDto;
import kr.mashup.branding.domain.invite.Invite;
import kr.mashup.branding.domain.member.Platform;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(staticName = "of")
public class InviteDto {

    private String code;

    private Platform platform;

    private GenerationDto generation;

    private int limitCount;

    private LocalDateTime startedAt;

    private LocalDateTime endedAt;

    public static InviteDto from(Invite invite){

        return InviteDto.of(
                invite.getCode(),
                invite.getPlatform(),
                GenerationDto.from(invite.getGeneration()),
                invite.getLimitCount(),
                invite.getStartedAt(),
                invite.getEndedAt());
    }
}
