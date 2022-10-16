package kr.mashup.branding.domain.application;

import org.springframework.data.domain.Pageable;

import kr.mashup.branding.domain.application.confirmation.ApplicantConfirmationStatus;
import kr.mashup.branding.domain.application.result.ApplicationInterviewStatus;
import kr.mashup.branding.domain.application.result.ApplicationScreeningStatus;
import lombok.Value;

/**
 * 지원서 목록 조회
 * 값이 null 이면 쿼리 조건에 추가하지 않는다.
 */
@Value(staticConstructor = "of")
public class ApplicationQueryRequest {
    Integer generationNumber;
    String searchWord;
    Long teamId;
    ApplicantConfirmationStatus confirmationStatus;
    ApplicationScreeningStatus screeningStatus;
    ApplicationInterviewStatus interviewStatus;
    Boolean isShowAll;
    Pageable pageable;

    public ApplicationQueryVo toVo(){
        return ApplicationQueryVo.of(
            searchWord,
            teamId,
            confirmationStatus,
            screeningStatus,
            interviewStatus,
            isShowAll,
            pageable);
    }
}
