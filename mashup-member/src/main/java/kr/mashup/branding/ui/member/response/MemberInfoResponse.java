package kr.mashup.branding.ui.member.response;

import kr.mashup.branding.dto.member.MemberDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class MemberInfoResponse {

    private Long id;

    private String name;

    private String identification;

    private String platform;

    private Integer generationNumber;

    public static MemberInfoResponse from(MemberDto memberDto) {
        return MemberInfoResponse.of(
            memberDto.getId(),
            memberDto.getName(),
            memberDto.getIdentification(),
            memberDto.getPlatform().name(),
            memberDto.getGeneration());
    }

}
