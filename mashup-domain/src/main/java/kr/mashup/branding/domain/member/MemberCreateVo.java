package kr.mashup.branding.domain.member;

import kr.mashup.branding.domain.generation.Generation;
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

    private Generation generation;

    private Boolean privatePolicyAgreed;

    public Member toEntity(PasswordEncoder encoder){
        return Member.of(name, identification, password, encoder, platform, generation, privatePolicyAgreed);
    }

}
