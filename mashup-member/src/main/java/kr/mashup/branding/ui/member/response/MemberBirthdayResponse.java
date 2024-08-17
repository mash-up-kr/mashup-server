package kr.mashup.branding.ui.member.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.MonthDay;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class MemberBirthdayResponse {

    @JsonFormat(pattern = "MM-dd")
    private final MonthDay date;
    private final List<Member> members;

    @Getter
    @RequiredArgsConstructor
    public static class Member {
        private final long id;
        private final String name;
        private final String platform;
        private final boolean congratulated;
    }
}
