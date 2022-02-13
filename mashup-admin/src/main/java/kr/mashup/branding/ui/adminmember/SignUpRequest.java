package kr.mashup.branding.ui.adminmember;

import kr.mashup.branding.domain.adminmember.Position;
import lombok.Data;

@Data
public class SignUpRequest {
    private String username;
    private String password;
    private String phoneNumber;
    private String description;
    private Position position;
}
