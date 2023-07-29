package kr.mashup.branding.ui.adminmember.vo;

import lombok.Getter;

import java.util.List;

@Getter
public class AdminPasswordResetRequest {

    private List<Long> adminIds;
    private String resetPassword;

}
