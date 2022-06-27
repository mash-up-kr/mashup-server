package kr.mashup.branding.service.member.dto;

import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.generation.GenerationVo;
import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.member.Platform;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@AllArgsConstructor(staticName = "of")
public class MemberCreateVo {

    private String name;

    private String identification;

    private String password;

    private Platform platform;

    private GenerationVo generation;

    private Boolean privatePolicyAgreed;


}
