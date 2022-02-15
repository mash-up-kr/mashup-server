package kr.mashup.branding.domain.application.form;

import org.springframework.data.domain.Pageable;

import lombok.Value;

@Value(staticConstructor = "of")
public class ApplicationFormQueryVo {
    /**
     * 팀 식별자
     */
    Long teamId;
    /**
     * 검색어 (설문지 이름)
     */
    String searchWord;
    /**
     * 페이지 정보
     * 정렬 (이름, 생성시각, 수정시각)
     */
    Pageable pageable;
}
