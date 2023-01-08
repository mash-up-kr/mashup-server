package kr.mashup.branding.ui.member.request;

import kr.mashup.branding.domain.member.Platform;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class SignUpRequest {

    @Length(message = "아이디는 5자 이상 15자 이하여야 합니다.", min = 5, max=15)
    private String identification;

    @NotBlank(message = "이름은 비어있을 수 없습니다.")
    private String name;

    @Length(message = "비밀번호는 8자 이상 30자 이하여야 합니다.", min = 8, max=30)
    private String password;

    @NotNull(message = "플랫폼은 비어있을 수 없습니다.")
    private Platform platform;

    @NotBlank(message = "가입 코드는 비어있을 수 없습니다.")
    private String inviteCode;

    @AssertTrue(message = "개인정보 이용에 동의해야 가입할 수 있습니다.")
    private Boolean privatePolicyAgreed;
}
