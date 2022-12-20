package kr.mashup.branding.ui.emailnotification;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class EmailSendRequest {

    private String memo;

    private String templateName;

    private List<Long> applicationIds;
}
