package kr.mashup.branding.ui.application.vo;

import java.util.List;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UpdateApplicationResultsRequest {
    private List<Long> applicationIds;
    private ApplicationResultStatus applicationResultStatus;
}
