package kr.mashup.branding.service.member;

import kr.mashup.branding.domain.member.Platform;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class MemberBirthdayDto {

    private Long memberId;
    private String name;
    private Platform platform;
    private LocalDate birthDate;
}
