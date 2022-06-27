package kr.mashup.branding.domain.invite;

import com.sun.istack.NotNull;
import kr.mashup.branding.domain.generation.GenerationVo;
import kr.mashup.branding.domain.member.Platform;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(staticName = "of")
public class InviteVo {

    private String code;

    private Platform platform;

    private GenerationVo generation;

    private int limitCount;

    private LocalDateTime startedAt;

    private LocalDateTime endedAt;

    public static InviteVo from(Invite invite){

        return InviteVo.of(
                invite.getCode(),
                invite.getPlatform(),
                GenerationVo.from(invite.getGeneration()),
                invite.getLimitCount(),
                invite.getStartedAt(),
                invite.getEndedAt());
    }
}
