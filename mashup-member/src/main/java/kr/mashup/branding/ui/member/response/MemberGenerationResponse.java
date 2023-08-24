package kr.mashup.branding.ui.member.response;

import kr.mashup.branding.domain.member.MemberGeneration;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberGenerationResponse {

    private Long id;                    // 기수

    private Integer number;             // 기수

    private String platform;            // 플랫폼

    private String projectTeamName;     // 프로젝트 팀

    private String role;                // 역할

    public static MemberGenerationResponse of(MemberGeneration memberGeneration) {
        return new MemberGenerationResponse(
                memberGeneration.getId(),
                memberGeneration.getGeneration().getNumber(),
                memberGeneration.getPlatform().getName(),
                memberGeneration.getProjectTeamName(),
                memberGeneration.getRole()
        );
    }
}
