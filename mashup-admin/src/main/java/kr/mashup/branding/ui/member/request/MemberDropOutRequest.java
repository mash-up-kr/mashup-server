package kr.mashup.branding.ui.member.request;

import lombok.Getter;

import java.util.List;

@Getter
public class MemberDropOutRequest {

    private List<Long> memberIds;
    private Integer generationNumber;
}
