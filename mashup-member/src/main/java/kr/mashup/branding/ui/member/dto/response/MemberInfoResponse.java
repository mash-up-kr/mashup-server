package kr.mashup.branding.ui.member.dto.response;

import kr.mashup.branding.service.attendanceCode.dto.MemberVo;
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

    public static MemberInfoResponse from(MemberVo memberVo){
        return MemberInfoResponse.of(
                memberVo.getId(),
                memberVo.getName(),
                memberVo.getIdentification(),
                memberVo.getPlatform().name(),
                memberVo.getGeneration());
    }

}
