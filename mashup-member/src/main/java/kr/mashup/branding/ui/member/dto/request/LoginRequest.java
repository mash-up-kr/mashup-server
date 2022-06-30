package kr.mashup.branding.ui.member.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginRequest {

    @NotBlank(message = "아이디는 비어있을 수 없습니다.")
    private String identification;

    @NotBlank(message = "비밀번호는 비어있을 수 없습니다.")
    private String password;
}
