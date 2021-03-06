package kr.mashup.branding.ui.member.response;

import kr.mashup.branding.domain.member.Member;
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

    public static MemberInfoResponse from(Member member) {
        return MemberInfoResponse.of(
            member.getId(),
            member.getName(),
            member.getIdentification(),
            member.getPlatform().name(),
            member.getGeneration().getNumber()
        );
    }

}
