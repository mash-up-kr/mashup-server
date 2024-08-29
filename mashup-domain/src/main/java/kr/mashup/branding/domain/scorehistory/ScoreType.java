package kr.mashup.branding.domain.scorehistory;

import com.fasterxml.jackson.annotation.JsonCreator;
import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum ScoreType {
    ATTENDANCE("전체 세미나 출석", 0.0),
    ABSENT("전체 세미나 결석", -1.0),
    LATE("전체 세미나 지각", -0.5),
    DEPLOY_FAILURE("프로젝트 배포 실패", -0.5),
    @Deprecated
    DEPLOY_SUCCESS("프로젝트 배포 성공", 1.0),
    SEMINAR_PRESENTATION("전체 세미나 발표", 1.0),
    @Deprecated
    TECH_BLOG_WRITE("기술블로그 작성", 1.0),
    MASHUP_CONTENT_WRITE("Mah-Up 콘텐츠 글 작성", 0.5),
    PROJECT_LEADER("프로젝트 팀 리더", 2.0),
    @Deprecated
    PROJECT_SUBLEADER("프로젝트 부팀장", 2.0),
    HACKATHON_COMMITTEE("해커톤 준비 위원회", 1.0),
    ADD_SCORE_DURING_SEMINAR_ACTIVITY_0_5("세미나 활동 중 추가 점수", 0.5),
    ADD_SCORE_DURING_SEMINAR_ACTIVITY_1("세미나 활동 중 추가 점수", 1d),
    MASHUP_STAFF("스태프", 3d),
    @Deprecated
    MASHUP_LEADER("회장", 100.0),
    @Deprecated
    MASHUP_SUBLEADER("부회장", 100.0),
    ETC("기타", 0.0),
    DEFAULT("기본 점수", 3.0),
    ;

    private final String name;
    private final Double score;

    ScoreType(String name, Double score) {
        this.name = name;
        this.score = score;
    }

    @JsonCreator
    public static ScoreType from(String s) {
        try {
            return ScoreType.valueOf(s.toUpperCase());
        } catch (Exception e) {
            log.info("platform type conversion error : {}", e.getMessage());
            throw new BadRequestException(ResultCode.SCORETYPE_INVALID_NAME);
        }
    }
    public String getName() {
        return name;
    }

    public Double getScore() {
        return score;
    }
}
