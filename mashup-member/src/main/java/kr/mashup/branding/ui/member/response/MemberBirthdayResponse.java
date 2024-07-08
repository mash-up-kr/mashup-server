package kr.mashup.branding.ui.member.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class MemberBirthdayResponse {

    private final LocalDate date;
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
