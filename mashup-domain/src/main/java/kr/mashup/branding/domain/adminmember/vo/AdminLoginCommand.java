package kr.mashup.branding.domain.adminmember.vo;

import lombok.Value;

@Value(staticConstructor = "of")
public class AdminLoginCommand {
    String username;
    String password;
}
