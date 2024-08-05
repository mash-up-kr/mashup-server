package kr.mashup.branding.service.member;

import kr.mashup.branding.domain.member.Platform;
import lombok.Getter;

import java.time.LocalDate;
import java.time.MonthDay;

@Getter
public class MemberBirthdayDto {

    private final Long memberId;
    private final String name;
    private final Platform platform;
    private final MonthDay birthDate;

    public MemberBirthdayDto(Long memberId, String name, Platform platform, LocalDate birthDate) {
        this.memberId = memberId;
        this.name = name;
        this.platform = platform;
        this.birthDate = MonthDay.from(birthDate);
    }
}
