package kr.mashup.branding.ui.member.response;

import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.member.Platform;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AccessResponse {

    private Long memberId;

    private String token;

    private String name;

    private String platform;
    // 정렬 ASC
    private List<Integer> generations;

    private Boolean pushNotificationAgreed;

    public static AccessResponse of(Member member, Platform platform, String token) {
        return new AccessResponse(
                member.getId(),
                token,
                member.getName(),
                platform.name(),
                member.getMemberGenerations()
                        .stream()
                        .map(it-> it.getGeneration().getNumber())
                        .sorted()
                        .collect(Collectors.toList()),
                member.getPushNotificationAgreed());
    }

}
