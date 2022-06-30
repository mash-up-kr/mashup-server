package kr.mashup.branding.ui.member.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class LoginRequest {

    @NotBlank(message = "아이디는 비어있을 수 없습니다.")
    private String identification;

    @NotBlank(message = "비밀번호는 비어있을 수 없습니다.")
    private String password;
}
