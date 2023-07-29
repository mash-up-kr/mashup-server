package kr.mashup.branding.ui.member.response;

import kr.mashup.branding.domain.member.MemberStatus;
import kr.mashup.branding.ui.scorehistory.response.ScoreHistoryResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberDetailResponse {

    private String name;
    private String identification;
    private Integer generationNumber;
    private String platform;
    private List<ScoreHistoryResponse> scoreHistoryResponses;
    private Double totalScore;
    private MemberStatus memberStatus;

    public static MemberDetailResponse of(String name, String identification, Integer generationNumber, String platform, List<ScoreHistoryResponse> scoreHistoryResponses, MemberStatus memberStatus) {
        return new MemberDetailResponse(name, identification, generationNumber, platform, scoreHistoryResponses, scoreHistoryResponses.stream().filter(it->!it.getIsCanceled()).mapToDouble(ScoreHistoryResponse::getScore).sum(), memberStatus);
    }
}
