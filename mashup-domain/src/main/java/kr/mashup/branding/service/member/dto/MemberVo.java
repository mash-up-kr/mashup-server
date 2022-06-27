package kr.mashup.branding.service.member.dto;

import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.member.Platform;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class MemberVo {

    private Long id;

    private String name;

    private String identification;

    private Platform platform;

    private Integer generation;

    public static MemberVo from(Member member){
        return MemberVo.of(
                member.getId(),
                member.getName(),
                member.getIdentification(),
                member.getPlatform(),
                member.getGeneration().getNumber());
    }

}
