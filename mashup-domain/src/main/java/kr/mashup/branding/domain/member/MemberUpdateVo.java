package kr.mashup.branding.domain.member;

import kr.mashup.branding.domain.generation.Generation;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class MemberUpdateVo {

    private String name;

    private Platform platform;

    private Generation generation;
}
