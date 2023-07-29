package kr.mashup.branding.domain.adminmember.vo;

import kr.mashup.branding.domain.adminmember.entity.Position;
import kr.mashup.branding.domain.adminmember.exception.AdminMemberSignUpRequestInvalidException;
import lombok.Value;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@Value(staticConstructor = "of")
public class AdminMemberSignUpCommand {

    String username;
    String password;
    Position position;

    private void validate(AdminMemberSignUpCommand adminMemberSignUpCommand) {
        Assert.notNull(adminMemberSignUpCommand, "'adminMemberVo' must not be null");

        if (!StringUtils.hasText(adminMemberSignUpCommand.getUsername())) {
            throw new AdminMemberSignUpRequestInvalidException("'username' must not be null, empty or blank");
        }
        if (!StringUtils.hasText(adminMemberSignUpCommand.getPassword())) {
            throw new AdminMemberSignUpRequestInvalidException("'password' must not be null, empty or blank");
        }
    }
}
