package kr.mashup.branding.domain.adminmember.vo;

import kr.mashup.branding.domain.adminmember.entity.Position;
import kr.mashup.branding.domain.adminmember.exception.AdminMemberSignUpRequestInvalidException;
import kr.mashup.branding.domain.adminmember.exception.AdminMemberUsernameDuplicatedException;
import lombok.Value;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@Value(staticConstructor = "of")
public class AdminMemberSignUpCommand {

    String username;
    String password;
    String phoneNumber;
    Position position;

    private void validate(AdminMemberSignUpCommand adminMemberSignUpCommand) {
        Assert.notNull(adminMemberSignUpCommand, "'adminMemberVo' must not be null");

        if (!StringUtils.hasText(adminMemberSignUpCommand.getUsername())) {
            throw new AdminMemberSignUpRequestInvalidException("'username' must not be null, empty or blank");
        }
        if (!StringUtils.hasText(adminMemberSignUpCommand.getPassword())) {
            throw new AdminMemberSignUpRequestInvalidException("'password' must not be null, empty or blank");
        }
        if (adminMemberSignUpCommand.getPhoneNumber() != null && adminMemberSignUpCommand.getPhoneNumber().length() > 13) {
            throw new AdminMemberSignUpRequestInvalidException(
                "'phoneNumber' length must be less than or equal to 13");
        }
        if (adminMemberSignUpCommand.getPhoneNumber().replaceAll("-", "").length() != 11) {
            throw new AdminMemberSignUpRequestInvalidException(
                "'phoneNumber' format must include hyphen('-').");
        }

    }
}
