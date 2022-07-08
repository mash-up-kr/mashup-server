package kr.mashup.branding.domain.scoreHistory;

public enum Title {
    ATTENDANCE("출석"),
    ABSENT("결석"),
    LATE("지각"),
    DEPLOY_FAIL("프로젝트 배포 실패"),
    DEPLOY_SUCCESS("프로젝트 배포 성공"),
    SEMINAR_PRESENTATION("전체 세미나 발표"),
    PROJECT_LEADER("프로젝트팀 팀장"),
    PROJECT_SUBLEADER("프로젝트 부팀장"),
    HACKATHON_COMMITTEE("해커톤 준비 위원회"),
    TECH_BLOG_WRITE("기술블로그 작성"),
    MASHUP_CONTENT_WRITE("Mah-Up 콘텐츠 글 작성"),
    MASHUP_LEADER("13기 회장"),
    MASHUP_SUBLEADER("13기 부회장"),
    ;

    Title(String description) {
    }
}
