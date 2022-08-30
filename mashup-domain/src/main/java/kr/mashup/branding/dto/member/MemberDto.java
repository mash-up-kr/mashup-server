package kr.mashup.branding.dto.member;

import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.member.Platform;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor(staticName = "of")
public class MemberDto {

    private Long id;

    private String name;

    private String identification;

    private Platform platform;

    private List<Integer> generation;

    public static MemberDto from(Member member) {
        return MemberDto.of(
                member.getId(),
                member.getName(),
                member.getIdentification(),
                member.getPlatform(),
                member.getMemberGenerations()
                        .stream()
                        .map(it -> it.getGeneration().getNumber())
                        .collect(Collectors.toList()));
    }

}
