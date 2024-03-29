package kr.mashup.branding.domain.recruitmentschedule;

public enum RecruitmentScheduleEventName {
    RECRUITMENT_STARTED("RECRUITMENT_STARTED"),
    RECRUITMENT_ENDED("RECRUITMENT_ENDED"),
    SCREENING_RESULT_ANNOUNCED("SCREENING_RESULT_ANNOUNCED"),
    INTERVIEW_START("INTERVIEW_START"),
    INTERVIEW_END("INTERVIEW_END"),
    INTERVIEW_RESULT_ANNOUNCED("INTERVIEW_RESULT_ANNOUNCED"),
    AFTER_FIRST_SEMINAR_JOIN("AFTER_FIRST_SEMINAR_JOIN");

    RecruitmentScheduleEventName(String desc) {}
}
