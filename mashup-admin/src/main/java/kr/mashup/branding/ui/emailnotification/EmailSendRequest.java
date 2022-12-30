package kr.mashup.branding.ui.emailnotification;

import kr.mashup.branding.domain.email.EmailTemplateName;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class EmailSendRequest {

    private String memo;

    private EmailTemplateName templateName;

    private List<Long> applicationIds;
}
