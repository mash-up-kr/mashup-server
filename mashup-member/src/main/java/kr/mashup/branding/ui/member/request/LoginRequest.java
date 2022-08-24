package kr.mashup.branding.ui.member.request;

import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
public class LoginRequest {

    @Length(message = "아이디는 5자 이상 15자 이하여야 합니다.", min = 5, max=15)
    private String identification;

    @Length(message = "비밀번호는 8자 이상 30자 이하여야 합니다.", min = 8, max=30)
    private String password;
}
