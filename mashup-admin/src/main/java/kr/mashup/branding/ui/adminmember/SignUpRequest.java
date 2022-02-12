package kr.mashup.branding.ui.adminmember;

import lombok.Data;

@Data
public class SignUpRequest {
    private String username;
    private String password;
    private String phoneNumber;
    private String description;
}
