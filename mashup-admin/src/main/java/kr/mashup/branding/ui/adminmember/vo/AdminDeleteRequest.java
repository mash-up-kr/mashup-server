package kr.mashup.branding.ui.adminmember.vo;

import lombok.Getter;

import java.util.List;

@Getter
public class AdminDeleteRequest {
    List<Long> adminIds;
}
