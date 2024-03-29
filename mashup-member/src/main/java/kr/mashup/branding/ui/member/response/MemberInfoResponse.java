package kr.mashup.branding.ui.member.response;

import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.member.Platform;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor(staticName = "of")
public class MemberInfoResponse {

    private Long id;

    private String name;

    private String identification;

    private String platform;

    private List<Integer> generations;

    private Boolean newsPushNotificationAgreed;

    private Boolean danggnPushNotificationAgreed;

    public static MemberInfoResponse from(Member member, Platform platform) {
        return MemberInfoResponse.of(
                member.getId(),
                member.getName(),
                member.getIdentification(),
                platform.name(),
                member.getMemberGenerations()
                        .stream()
                        .map(it -> it.getGeneration().getNumber())
                        .collect(Collectors.toList()),
                member.getNewsPushNotificationAgreed(),
                member.getDanggnPushNotificationAgreed()
        );
    }
}
