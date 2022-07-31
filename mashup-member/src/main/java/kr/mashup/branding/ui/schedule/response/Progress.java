package kr.mashup.branding.ui.schedule.response;

public enum Progress {
    NOT_REGISTERED, // 등록된 세미나가 없는 경우
    ON_GOING, // 세미나가 존재하지만, 현재 날짜 이후로 등록된 세미나가 있는 경우
    DONE; // 세미나가 존재하지만, 현재 날짜 이후로 등록된 세미나가 없는 경우
}
