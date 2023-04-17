package kr.mashup.branding.ui.danggn.response;

import lombok.Getter;
import lombok.Value;

import java.util.List;

@Getter
@Value(staticConstructor = "of")
public class DanggnMemberRankResponse {
    List<DanggnMemberRankData> danggnMemberRankDataList;

    Integer limit;
}
