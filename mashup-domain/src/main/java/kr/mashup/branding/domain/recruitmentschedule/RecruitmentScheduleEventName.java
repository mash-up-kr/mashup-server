package kr.mashup.branding.domain.recruitmentschedule;

public enum RecruitmentScheduleEventName {
    RECRUITMENT_STARTED("RECRUITMENT_STARTED"),
    RECRUITMENT_ENDED("RECRUITMENT_ENDED"),
    SCREENING_RESULT_ANNOUNCED("SCREENING_RESULT_ANNOUNCED"),
    INTERVIEW_RESULT_ANNOUNCED("INTERVIEW_RESULT_ANNOUNCED");

    RecruitmentScheduleEventName(String desc) {}
}
