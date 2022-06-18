package kr.mashup.branding.domain.member;

import kr.mashup.branding.domain.generation.Generation;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class MemberVo {

    private Long id;

    private String name;

    private String identification;

    private Platform platform;

    private Generation generation;

    public static MemberVo from(Member member){
        return MemberVo.of(
                member.getId(),
                member.getName(),
                member.getIdentification(),
                member.getPlatform(),
                member.getGeneration());
    }

}
