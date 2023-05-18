package kr.mashup.branding.ui.member.request;

import kr.mashup.branding.domain.member.OsType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter // temp
public class LoginRequest {

    //@Length(message = "아이디는 5자 이상 15자 이하여야 합니다.", min = 5, max=15)
    private String identification;

    private String password;

    @NotNull
    private OsType osType;

    @NotEmpty
    private String fcmToken;
}
