package kr.mashup.branding.dto.member;
import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.member.Platform;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class MemberUpdateDto {

    private String name;

    private Platform platform;

    private Generation generation;
}
