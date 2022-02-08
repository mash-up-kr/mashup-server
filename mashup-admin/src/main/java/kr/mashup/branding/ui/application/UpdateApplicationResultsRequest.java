package kr.mashup.branding.ui.application;

import java.util.List;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UpdateApplicationResultsRequest {
    private List<Long> applicationIds;
    private ApplicationResultStatusRequest applicationResultStatus;
}
