package kr.mashup.branding.ui.member.response;

import kr.mashup.branding.domain.member.Platform;
import kr.mashup.branding.ui.scorehistory.response.ScoreHistoryResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

import java.util.List;
import java.util.function.BinaryOperator;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberDetailResponse {

    private String name;
    private String identification;
    private Integer generationNumber;
    private String platform;
    private List<ScoreHistoryResponse> scoreHistoryResponses;
    private Double totalScore;

    public static MemberDetailResponse of(String name, String identification, Integer generationNumber, String platform, List<ScoreHistoryResponse> scoreHistoryResponses) {
        return new MemberDetailResponse(name, identification, generationNumber, platform, scoreHistoryResponses, scoreHistoryResponses.stream().mapToDouble(ScoreHistoryResponse::getScore).sum());
    }
}
