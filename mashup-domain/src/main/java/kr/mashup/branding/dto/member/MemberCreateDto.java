package kr.mashup.branding.dto.member;

import kr.mashup.branding.dto.generation.GenerationDto;
import kr.mashup.branding.domain.member.Platform;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class MemberCreateDto {

    private String name;

    private String identification;

    private String password;

    private Platform platform;

    private GenerationDto generation;

    private Boolean privatePolicyAgreed;


}
