package kr.mashup.branding.service.attendanceCode.dto;
import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.member.Platform;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class MemberUpdateVo {

    private String name;

    private Platform platform;

    private Generation generation;
}
