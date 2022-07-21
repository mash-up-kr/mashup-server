package kr.mashup.branding.domain.scorehistory;

public enum ScoreType {
    ATTENDANCE("출석", 1.0),
    ABSENT("결석", -1.0),
    LATE("지각", -0.5),
    DEPLOY_FAIL("프로젝트 배포 실패", -0.5),
    DEPLOY_SUCCESS("프로젝트 배포 성공", 1.0),
    SEMINAR_PRESENTATION("전체 세미나 발표", 1.0),
    PROJECT_LEADER("프로젝트팀 팀장", 2.0),
    PROJECT_SUBLEADER("프로젝트 부팀장", 2.0),
    HACKATHON_COMMITTEE("해커톤 준비 위원회", 1.0),
    TECH_BLOG_WRITE("기술블로그 작성", 1.0),
    MASHUP_CONTENT_WRITE("Mah-Up 콘텐츠 글 작성", 0.5),
    MASHUP_LEADER("회장", 999.0),
    MASHUP_SUBLEADER("부회장", 999.0),
    ETC("기타", 0.0),
    ;

    private final String name;
    private final Double score;

    ScoreType(String name, Double score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public Double getScore() {
        return score;
    }
}
